package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.TeacherEntity;

public interface TeacherService extends BaseService<TeacherEntity> {
    TeacherEntity register(TeacherEntity teacherEntity);
}
