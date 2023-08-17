package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.model.request.TeacherRequest;
import com.kits_internship.edu_flatform.model.response.TeacherResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;

import java.util.Optional;

public interface TeacherService extends BaseService<TeacherEntity> {
    TeacherEntity register(TeacherEntity teacherEntity);

    TeacherEntity getTeacherInfo(Optional<UserPrinciple> user);

    TeacherEntity updateInfo(TeacherRequest request, Optional<UserPrinciple> user);
}
