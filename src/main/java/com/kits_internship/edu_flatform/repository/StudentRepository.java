package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.StudentEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends BaseRepository<StudentEntity,Long> {
    Optional<StudentEntity> findByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM student WHERE userID = ?1")
    StudentEntity findByUserID(Long id);
}
