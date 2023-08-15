package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @PostMapping("/add")
    private UserEntity addAccount(@RequestBody UserEntity request) {
        UserEntity userEntity = userService.createAccount(request);
        return userEntity;
    }
}
