package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.RoleName;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, BaseRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query("select t from UserEntity t where t.email = ?1 or t.username = ?2")
    UserEntity findByEmailOrUsername(String email, String username);

    Optional<UserEntity> findByUsername(String username);
}
