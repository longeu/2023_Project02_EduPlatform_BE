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
import com.kits_internship.edu_flatform.ulti.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TeacherServiceImpl extends BaseServiceImpl<TeacherEntity, TeacherRepository> implements TeacherService {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DateConfig dateConfig;
    @Autowired
    FileUtils fileUtils;

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
                throw new NotFoundException("Not found user");
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

    @Override
    public ResponseEntity uploadFile(MultipartFile multipartFile, Optional<UserPrinciple> user) {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(multipartFile);
        fileUtils.validateFiles(multipartFiles);
        try {
            String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();

            File file = fileUtils.convertToFile(multipartFile, fileName);
            String FILE_URL = "";
            if (multipartFile.getContentType().startsWith("video")) {
                FILE_URL = fileUtils.uploadVideo(file, "video/" + fileName);
            } else if (multipartFile.getContentType().startsWith("image")) {
                FILE_URL = fileUtils.uploadVideo(file, "image/" + fileName);
            } else {
                String[] parts = multipartFile.getContentType().split("\\.");
                String lastPart = parts[parts.length - 1];
                if (lastPart.equals("sheet")) {
                    FILE_URL = fileUtils.uploadVideo(file, "xlsx/" + fileName);
                } else if (lastPart.equals("document")) {
                    FILE_URL = fileUtils.uploadVideo(file, "docx/" + fileName);
                }
            }
            file.delete();
            return ResponseEntity.status(HttpStatus.OK).body(FILE_URL);
        } catch (Exception e) {
            log.error(e.getMessage());
            errors.put("errors", e.getMessage());
            errors.put("file", "files upload fail!");
            throw new UnprocessableEntityException(errors);
        }
    }
}
