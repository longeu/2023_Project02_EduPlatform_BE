package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.model.request.TeacherRequest;
import com.kits_internship.edu_flatform.model.response.TeacherResponse;
import com.kits_internship.edu_flatform.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/{id}")
    private TeacherResponse getTeacherInfo(@PathVariable Long id){
        TeacherEntity teacherEntity = teacherService.findById(id);
        return modelMapper.map(teacherEntity,TeacherResponse.class);
    }


    @PutMapping("/update/{id}")
    private TeacherResponse updateTeacherInfo(@RequestBody TeacherRequest request,@PathVariable Long id){
        TeacherEntity teacherEntity = modelMapper.map(request,TeacherEntity.class);
        TeacherEntity response = teacherService.update(id,teacherEntity);
        return modelMapper.map(response,TeacherResponse.class);
    }
}
