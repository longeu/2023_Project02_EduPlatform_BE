package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.config.DateConfig;
import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.request.TeacherRequest;
import com.kits_internship.edu_flatform.repository.TeacherRepository;
import com.kits_internship.edu_flatform.repository.UserRepository;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.security.jwt.JwtService;
import com.kits_internship.edu_flatform.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TeacherServiceImpl extends BaseServiceImpl<TeacherEntity, TeacherRepository> implements TeacherService {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DateConfig dateConfig;

    public TeacherServiceImpl(TeacherRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public TeacherEntity register(TeacherEntity teacherEntity) {
        Optional<TeacherEntity> existTeacher = jpaRepository.findByEmail(teacherEntity.getEmail());
        if (existTeacher.isPresent()) {
            errors.put("teacher", "existed!");
            throw new UnprocessableEntityException(errors);
        }
        teacherEntity.setCreatedDate(dateConfig.getTimestamp());
        teacherEntity.setModifiedDate(dateConfig.getTimestamp());
        TeacherEntity response = jpaRepository.save(teacherEntity);
        return response;
    }

    @Override
    public TeacherEntity getTeacherInfo(Optional<UserPrinciple> user) {

        try {
            UserPrinciple userPrinciple = user.orElseThrow();
            Optional<UserEntity> userEntity = userRepository.findByUsername(userPrinciple.getUsername());
            if (userEntity.isEmpty()) {
                throw new NotFoundException("Not found user!");
            }
            Optional<TeacherEntity> teacherEntity = jpaRepository.findByUserID(userEntity.get().getId());
            if (teacherEntity.isEmpty()) {
                throw new NotFoundException("Not Found Teacher!");
            }
            return teacherEntity.get();
        } catch (Exception e) {
            errors.put("base", e.getMessage());
            throw new NotFoundException(errors);
        }
    }

    @Override
    public TeacherEntity updateInfo(TeacherRequest request, Optional<UserPrinciple> user) {
        TeacherEntity teacherEntity = getTeacherInfo(user);
        teacherEntity.setPhone(request.getPhone());
        teacherEntity.setEmail(request.getEmail());
        teacherEntity.setBio(request.getBio());
        teacherEntity.setCertificates(request.getCertificates());
        teacherEntity.setExperience(request.getExperience());
        teacherEntity.setFirstName(request.getFirstName());
        teacherEntity.setLastName(request.getLastName());
        teacherEntity.setLink(request.getLink());
        teacherEntity.setImage(request.getImage());
        teacherEntity.setModifiedDate(dateConfig.getTimestamp());

        jpaRepository.save(teacherEntity);
        return teacherEntity;
    }
}
