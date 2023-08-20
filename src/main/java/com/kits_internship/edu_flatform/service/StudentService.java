package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.StudentEntity;
import com.kits_internship.edu_flatform.model.request.StudentRequest;
import com.kits_internship.edu_flatform.security.UserPrinciple;

import java.util.Optional;

public interface StudentService {
    StudentEntity register(StudentEntity studentEntity);

    StudentEntity getStudentInfo(Optional<UserPrinciple> user);

    StudentEntity updateInfo(StudentRequest request, Optional<UserPrinciple> user);
}
