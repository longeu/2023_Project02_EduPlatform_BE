package com.kits_internship.edu_flatform.repository;

import com.kits_internship.edu_flatform.entity.StudentEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends BaseRepository<StudentEntity,Long> {
}
