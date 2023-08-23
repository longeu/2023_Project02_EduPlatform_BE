package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.model.base.BasePagingQueryRequest;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.CourseTransactionRequest;
import com.kits_internship.edu_flatform.model.response.CourseTracsactionResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student/transaction")
public class TransactionController extends BaseController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/coursePay")
    private CourseTracsactionResponse coursePay(@RequestBody CourseTransactionRequest request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return transactionService.courseTransaction(request, user);
    }

    @GetMapping("/list")
    private ListResponseModel listCoursePay(BasePagingQueryRequest request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return transactionService.listCoursePay(request, user);
    }

    @GetMapping("/{id}")
    private CourseTracsactionResponse getCoursePayById(@PathVariable Long id, Principal currentUser) {
        Optional<UserPrinciple > user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        return transactionService.getTransactionById(id, user);
    }
}
