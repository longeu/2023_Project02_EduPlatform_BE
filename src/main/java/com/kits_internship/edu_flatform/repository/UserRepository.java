package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.RoleName;
import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, BaseRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query("select t from UserEntity t where t.email = ?1 or t.username = ?2")
    UserEntity findByEmailOrUsername(String email, String username);

    Optional<UserEntity> findByUsername(String username);

    @Query(value = "select u from UserEntity u" +
            "   where " +
            "       (coalesce(:keyword) is null or :keyword = '' " +
            "       or lower(u.username) like concat('%', concat(lower(:keyword), '%'))" +
            "       or lower(u.email) like concat('%', concat(lower(:keyword), '%'))" +
            "       or lower(u.firstName) like concat('%', concat(lower(:keyword), '%'))" +
            "       or lower(u.lastName) like concat('%', concat(lower(:keyword), '%')))" +
            "       and (coalesce(:status,null) is null or u.status = :status ) "+
            "       and u.role in :roles"
    )
    Page<UserEntity> filter(
            @Param("keyword") String keyword,
            @Param("status") StatusName status,
            @Param("roles") List<RoleName> roles,
            Pageable pageable
    );
}
