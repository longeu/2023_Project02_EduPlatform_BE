package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.StudentEntity;
import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.model.request.StudentRequest;
import com.kits_internship.edu_flatform.model.request.TeacherRequest;

public interface StudentService extends BaseService<StudentEntity> {
    StudentEntity register(StudentEntity studentEntity);

    StudentEntity getStudentInfo(String tokenHeader);

    StudentEntity updateInfo(StudentRequest request, String token);
}
