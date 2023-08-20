package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.model.RegisterModel;
import com.kits_internship.edu_flatform.model.request.ActiveAccountRequest;
import com.kits_internship.edu_flatform.model.request.LoginRequest;
import com.kits_internship.edu_flatform.model.response.ActiveAccountResponse;
import com.kits_internship.edu_flatform.model.response.LoginResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/register")
    private RegisterModel addAccount(@RequestBody RegisterModel request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        UserEntity userMapper = modelMapper.map(request, UserEntity.class);
        UserEntity userEntity = userService.createAccount(userMapper,user);
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

    @PostMapping("/forgotPassword")
    private ResponseEntity forgotPassword(@RequestParam String email){
        ResponseEntity responseEntity = userService.forgotPassword(email);
        return responseEntity;
    }

    @PostMapping("/resetPassword")
    private ResponseEntity resetPassword(@RequestBody ActiveAccountRequest request){
        ResponseEntity responseEntity = userService.resetPassword(request);
        return responseEntity;
    }
}
