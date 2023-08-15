package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.model.request.TeacherRequest;
import com.kits_internship.edu_flatform.model.response.TeacherResponse;
import com.kits_internship.edu_flatform.security.jwt.JwtService;
import com.kits_internship.edu_flatform.service.TeacherService;
import com.kits_internship.edu_flatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
//@PreAuthorize("hasAuthority('ROLE_TEACHER')")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/info")
    private TeacherResponse getTeacherInfo(@RequestHeader("Authorization") String tokenHeader) {
        TeacherEntity entity = teacherService.getTeacherInfo(tokenHeader);
        TeacherResponse response = modelMapper.map(entity,TeacherResponse.class);
        response.setUserID(entity.getUser().getId());
        return response;
    }

    @PutMapping("/update")
    private TeacherResponse updateTeacherInfo(@RequestHeader("Authorization") String tokenHeader, @RequestBody TeacherRequest request) {
        TeacherEntity entity = teacherService.updateInfo(request, tokenHeader);
        TeacherResponse response = modelMapper.map(entity,TeacherResponse.class);
        response.setUserID(entity.getUser().getId());
        return response;
    }
}
