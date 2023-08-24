package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.model.base.BasePagingQueryRequest;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.CourseTransactionRequest;
import com.kits_internship.edu_flatform.model.request.ResultFilterRequest;
import com.kits_internship.edu_flatform.model.request.ResultRequest;
import com.kits_internship.edu_flatform.model.response.CourseTracsactionResponse;
import com.kits_internship.edu_flatform.model.response.ResultResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student/result")
public class ResultController extends BaseController {
    @Autowired
    ResultService resultService;

    @PostMapping("")
    private ResultResponse resultQuiz(@RequestBody ResultRequest request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return resultService.resultQuiz(request, user);
    }

    @GetMapping("/list")
    private ListResponseModel listResult(ResultFilterRequest request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return resultService.listResult(request, user);
    }

    @GetMapping("/{id}")
    private ResultResponse getById(@PathVariable Long id, Principal currentUser) {
        Optional<UserPrinciple > user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return resultService.getById(id, user);
    }
}
