package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.CourseFilterRequest;
import com.kits_internship.edu_flatform.model.request.CourseRequest;
import com.kits_internship.edu_flatform.model.response.CourseResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.CourseService;
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
@RequestMapping("/api/teacher/course")
public class CourseController extends BaseController {
    @Autowired
    CourseService courseService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/list")
    private ListResponseModel listCourses(CourseFilterRequest request, Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        ListResponseModel response = courseService.filterByCurrentUser(request, user);
        return response;
    }

    @GetMapping("/{id}")
    private CourseResponse getById(@PathVariable Long id, Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        Optional<CourseEntity> courseEntity = courseService.findByIdAndCurrentUser(id, user);
        if (courseEntity.isEmpty()) {
            errors.put("course", "Not found course");
            throw new NotFoundException(errors);
        }
        return modelMapper.map(courseEntity, CourseResponse.class);
    }

    @PostMapping("/add")
    private CourseResponse addCourse(@RequestBody CourseRequest courseRequest, Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return courseService.addByCurrentUser(courseRequest, user);
    }

    @PostMapping("/uploadFile")
    private ResponseEntity uploadFile(MultipartFile file, Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        if (file == null) {
            errors.put("file", "can't not be null");
            throw new UnprocessableEntityException(errors);
        }
        return courseService.uploadFile(file, user);
    }

    @PutMapping("/update/{id}")
    private CourseResponse updateCourse(@RequestBody CourseRequest request, @PathVariable Long id, Principal currentUser) {
        Map<String, Object> errors = new HashMap<>();
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return courseService.updateByCurrentUser(id, request, user);
    }

}
