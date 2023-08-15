package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.model.request.TeacherRequest;
import com.kits_internship.edu_flatform.model.response.TeacherResponse;

public interface TeacherService extends BaseService<TeacherEntity> {
    TeacherEntity register(TeacherEntity teacherEntity);

    TeacherEntity getTeacherInfo(String tokenHeader);

    TeacherEntity updateInfo(TeacherRequest request, String token);
}
