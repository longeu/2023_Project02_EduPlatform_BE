package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.TeacherEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends BaseRepository<TeacherEntity, Long> {
    Optional<TeacherEntity> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM teacher WHERE userID = ?1")
    TeacherEntity findByUserID(Long id);
}
