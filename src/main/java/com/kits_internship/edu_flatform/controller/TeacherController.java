package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.request.TeacherRequest;
import com.kits_internship.edu_flatform.model.response.TeacherResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.TeacherService;
import com.kits_internship.edu_flatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
public class TeacherController extends BaseController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/info")
    private TeacherResponse getTeacherInfo(Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        TeacherEntity entity = teacherService.getTeacherInfo(user);
        TeacherResponse response = modelMapper.map(entity, TeacherResponse.class);
        response.setUserID(entity.getUser().getId());
        return response;
    }

    @PutMapping("/update")
    private TeacherResponse updateTeacherInfo(@RequestBody TeacherRequest request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        TeacherEntity entity = teacherService.updateInfo(request, user);
        TeacherResponse response = modelMapper.map(entity, TeacherResponse.class);
        response.setUserID(entity.getUser().getId());
        return response;
    }


    @PostMapping("/uploadFile")
    private ResponseEntity uploadFile(MultipartFile file, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        if (file == null) {
            errors.put("file", "can't not be null");
            throw new UnprocessableEntityException(errors);
        }
        return teacherService.uploadFile(file, user);
    }
}
