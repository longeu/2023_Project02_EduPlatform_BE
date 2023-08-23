package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.config.DateConfig;
import com.kits_internship.edu_flatform.entity.StudentEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.request.StudentRequest;
import com.kits_internship.edu_flatform.repository.StudentRepository;
import com.kits_internship.edu_flatform.repository.UserRepository;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.security.jwt.JwtService;
import com.kits_internship.edu_flatform.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl extends BaseServiceImpl<StudentEntity, StudentRepository> implements StudentService {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DateConfig dateConfig;

    public StudentServiceImpl(StudentRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public StudentEntity register(StudentEntity studentEntity) {
        Optional<StudentEntity> existStudent = jpaRepository.findByEmail(studentEntity.getEmail());
        if (existStudent.isPresent()) {
            errors.put("student", "existed!");
            throw new UnprocessableEntityException(errors);
        }
        studentEntity.setCreatedDate(dateConfig.getTimestamp());
        studentEntity.setModifiedDate(dateConfig.getTimestamp());
        StudentEntity response = jpaRepository.save(studentEntity);
        return response;
    }

    @Override
    public StudentEntity getStudentInfo(Optional<UserPrinciple> user) {
        try {
            UserPrinciple userPrinciple = user.orElseThrow();
            Optional<UserEntity> userEntity = userRepository.findByUsername(userPrinciple.getUsername());
            if (userEntity.isEmpty()) {
                throw new NotFoundException("Not found user!");
            }
            Optional<StudentEntity> studentEntity = jpaRepository.findByUserID(userEntity.get().getId());
            if (studentEntity.isEmpty()) {
                throw new NotFoundException("Not Found student!");
            }
            return studentEntity.get();
        } catch (Exception e) {
            errors.put("base", e.getMessage());
            throw new NotFoundException(errors);
        }
    }

    @Override
    public StudentEntity updateInfo(StudentRequest request, Optional<UserPrinciple> user) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(request.getEmail());
        if(userEntity.isPresent()){
            errors.put("user","email have existed");
            throw new UnprocessableEntityException(errors);
        }
        StudentEntity studentEntity = getStudentInfo(user);
        studentEntity.setPhone(request.getPhone());
        studentEntity.setEmail(request.getEmail());
        studentEntity.setFirstName(request.getFirstName());
        studentEntity.setLastName(request.getLastName());
        studentEntity.setImage(request.getImage());
        studentEntity.setDescription(request.getDescription());
        studentEntity.setModifiedDate(dateConfig.getTimestamp());

        jpaRepository.save(studentEntity);
        return studentEntity;
    }
}
