package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.entity.StudentEntity;
import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.request.StudentRequest;
import com.kits_internship.edu_flatform.model.request.TeacherRequest;
import com.kits_internship.edu_flatform.repository.StudentRepository;
import com.kits_internship.edu_flatform.repository.TeacherRepository;
import com.kits_internship.edu_flatform.repository.UserRepository;
import com.kits_internship.edu_flatform.security.jwt.JwtService;
import com.kits_internship.edu_flatform.service.StudentService;
import com.kits_internship.edu_flatform.service.TeacherService;
import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl extends BaseServiceImpl<StudentEntity, StudentRepository> implements StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public StudentEntity register(StudentEntity studentEntity) {
        Map<String, Object> errors = new HashMap<>();
        Optional<StudentEntity> existStudent = studentRepository.findByEmail(studentEntity.getEmail());
        if (existStudent.isPresent()) {
            errors.put("Student", "existed!");
            throw new UnprocessableEntityException(errors);
        }
        return create(studentEntity);
    }

    @Override
    public StudentEntity getStudentInfo(String token) {
        Map<String, Object> errors = new HashMap<>();
        String jwt = token.replace("Bearer ", "");
        Claims claims = jwtService.extractAllClaims(jwt);
        String username = claims.getSubject();
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) {
            errors.put("user", "Not found user!");
            throw new NotFoundException(errors);
        }
        StudentEntity studentEntity = studentRepository.findByUserID(userEntity.get().getId());
        if (studentEntity == null) {
            errors.put("Student", "Not Found Student!");
            throw new NotFoundException(errors);
        }
        return studentEntity;
    }

    @Override
    public StudentEntity updateInfo(StudentRequest request, String token) {
        StudentEntity studentEntity = getStudentInfo(token);
        studentEntity = modelMapper.map(request, StudentEntity.class);
        studentEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        studentRepository.save(studentEntity);
        return studentEntity;
    }
}
