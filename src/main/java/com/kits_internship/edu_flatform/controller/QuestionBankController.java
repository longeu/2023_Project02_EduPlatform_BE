package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.QuestionBankEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.QuestionBankFilterRequest;
import com.kits_internship.edu_flatform.model.request.QuestionBankRequest;
import com.kits_internship.edu_flatform.model.response.QuestionBankResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.QuestionBankService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teacher/questionBank")
public class QuestionBankController extends BaseController {
    @Autowired
    QuestionBankService questionBankService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/list")
    private ListResponseModel listQuestionBank(QuestionBankFilterRequest request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }

        return questionBankService.filterByCurrentUser(request);
    }

    @GetMapping("/{id}")
    private QuestionBankResponse getById(@PathVariable Long id, @RequestParam Long lectureID, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        Optional<QuestionBankEntity> questionBankEntity = questionBankService.findByIdAndCurrentUser(id, lectureID, user);
        if (questionBankEntity.isEmpty()) {
            errors.put("questionBank", "Not found questionBank");
            throw new NotFoundException(errors);
        }
        return modelMapper.map(questionBankEntity, QuestionBankResponse.class);
    }

    @PostMapping("/add")
    private QuestionBankResponse addQuestionBank(@RequestBody QuestionBankRequest request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        if(request.getLectureID() == null){
            errors.put("base", "can't identify lectureID");
            throw new NotFoundException(errors);
        }
        return questionBankService.addByCurrentUser(request, user);
    }

    @PutMapping("/update/{id}")
    private QuestionBankResponse updateQuestionBank(@RequestBody QuestionBankRequest request, @PathVariable Long id, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return questionBankService.updateByCurrentUser(id, request, user);
    }
}
