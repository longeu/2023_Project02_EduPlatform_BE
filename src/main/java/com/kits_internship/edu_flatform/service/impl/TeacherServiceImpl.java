package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.request.TeacherRequest;
import com.kits_internship.edu_flatform.model.response.TeacherResponse;
import com.kits_internship.edu_flatform.repository.TeacherRepository;
import com.kits_internship.edu_flatform.repository.UserRepository;
import com.kits_internship.edu_flatform.security.jwt.JwtService;
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
public class TeacherServiceImpl extends BaseServiceImpl<TeacherEntity, TeacherRepository> implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

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

    @Override
    public TeacherEntity getTeacherInfo(String token) {
        Map<String, Object> errors = new HashMap<>();
        String jwt = token.replace("Bearer ", "");
        Claims claims = jwtService.extractAllClaims(jwt);
        String username = claims.getSubject();
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if(userEntity.isEmpty()){
            errors.put("user","Not found user!");
            throw new NotFoundException(errors);
        }
        TeacherEntity teacherEntity = teacherRepository.findByUserID(userEntity.get().getId());
        if(teacherEntity == null){
            errors.put("teacher", "Not Found Teacher!");
            throw new NotFoundException(errors);
        }
        return teacherEntity;
    }

    @Override
    public TeacherEntity updateInfo(TeacherRequest request, String token) {
        TeacherEntity teacherEntity = getTeacherInfo(token);
        teacherEntity.setPhone(request.getPhone());
        teacherEntity.setEmail(request.getEmail());
        teacherEntity.setBio(request.getBio());
        teacherEntity.setCertificates(request.getCertificates());
        teacherEntity.setExperience(request.getExperience());
        teacherEntity.setFirstName(request.getFirstName());
        teacherEntity.setLastName(request.getLastName());
        teacherEntity.setLink(request.getLink());
        teacherEntity.setImage(request.getImage());
        teacherEntity.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        teacherRepository.save(teacherEntity);
        return teacherEntity;
    }
}
