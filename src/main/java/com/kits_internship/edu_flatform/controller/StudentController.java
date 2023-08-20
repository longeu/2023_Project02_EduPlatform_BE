package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.StudentEntity;
import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.model.request.StudentRequest;
import com.kits_internship.edu_flatform.model.request.TeacherRequest;
import com.kits_internship.edu_flatform.model.response.StudentResponse;
import com.kits_internship.edu_flatform.model.response.TeacherResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.StudentService;
import com.kits_internship.edu_flatform.service.TeacherService;
import com.kits_internship.edu_flatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController extends BaseController {
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/info")
    private StudentResponse getTeacherInfo(Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        StudentEntity entity = studentService.getStudentInfo(user);
        StudentResponse response = modelMapper.map(entity, StudentResponse.class);
        response.setUserID(entity.getUser().getId());
        return response;
    }

    @PutMapping("/update")
    private StudentResponse updateTeacherInfo(@RequestBody StudentRequest request, Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        StudentEntity entity = studentService.updateInfo(request, user);
        StudentResponse response = modelMapper.map(entity, StudentResponse.class);
        response.setUserID(entity.getUser().getId());
        return response;
    }
}
