package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.model.RegisterModel;
import com.kits_internship.edu_flatform.model.request.ActiveAccountRequest;
import com.kits_internship.edu_flatform.model.request.LoginRequest;
import com.kits_internship.edu_flatform.model.response.ActiveAccountResponse;
import com.kits_internship.edu_flatform.model.response.LoginResponse;
import com.kits_internship.edu_flatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/register")
    private RegisterModel addAccount(@RequestBody RegisterModel request) {
        UserEntity userMapper = modelMapper.map(request, UserEntity.class);
        UserEntity userEntity = userService.createAccount(userMapper);
        return modelMapper.map(userEntity, RegisterModel.class);
    }

    @PostMapping("/activeAccount")
    private ResponseEntity activeAccount(@RequestBody ActiveAccountRequest request) {
        ResponseEntity response = userService.activeAccount(request);
        return response;
    }

    @PostMapping("/login")
    private LoginResponse login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return response;
    }
}
