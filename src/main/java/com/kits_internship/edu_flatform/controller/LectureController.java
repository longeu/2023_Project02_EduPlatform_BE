package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.LectureEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.LectureFilterRequest;
import com.kits_internship.edu_flatform.model.request.LectureRequest;
import com.kits_internship.edu_flatform.model.response.LectureResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher/lecture")
public class LectureController extends BaseController {
    @Autowired
    LectureService lectureService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/list")
    private ListResponseModel listLectures(LectureFilterRequest request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        if(request.getCourseID() == null){
            errors.put("base", "can't identify courseID");
            throw new NotFoundException(errors);
        }
        return lectureService.filterByCurrentUser(request);
    }

    @GetMapping("/{id}")
    private LectureResponse getById(@PathVariable Long id, @RequestParam Long courseID, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        Optional<LectureEntity> lectureEntity = lectureService.findByIdAndCurrentUser(id, courseID, user);
        if (lectureEntity.isEmpty()) {
            errors.put("lecture", "Not found lecture");
            throw new NotFoundException(errors);
        }
        return modelMapper.map(lectureEntity, LectureResponse.class);
    }

    @PostMapping("/add")
    private LectureResponse addLecture(@RequestBody LectureRequest request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return lectureService.addByCurrentUser(request, user);
    }

    @PutMapping("/update/{id}")
    private LectureResponse updateLecture(@RequestBody LectureRequest request, @PathVariable Long id, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return lectureService.updateByCurrentUser(id, request, user);
    }
}
