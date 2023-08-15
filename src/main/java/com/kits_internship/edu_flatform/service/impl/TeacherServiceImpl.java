package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.repository.TeacherRepository;
import com.kits_internship.edu_flatform.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TeacherServiceImpl extends BaseServiceImpl<TeacherEntity, TeacherRepository> implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public TeacherEntity register(TeacherEntity teacherEntity) {
        Map<String, Object> errors = new HashMap<>();
        Optional<TeacherEntity> existTeacher = teacherRepository.findByEmail(teacherEntity.getEmail());
        if (existTeacher.isPresent()) {
            errors.put("teacher", "existed!");
            throw new UnprocessableEntityException(errors);
        }
        return create(teacherEntity);
    }
}
