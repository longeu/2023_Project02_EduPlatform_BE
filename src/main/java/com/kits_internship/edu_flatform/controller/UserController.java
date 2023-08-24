package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.model.RegisterModel;
import com.kits_internship.edu_flatform.model.request.ActiveAccountRequest;
import com.kits_internship.edu_flatform.model.request.LoginRequest;
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

    @PostMapping("/resentOTP")
    private ResponseEntity resentOTP(@RequestParam String email) {
        return userService.resentOTP(email);
    }

    @PostMapping("/activeAccount")
    private ResponseEntity activeAccount(@RequestBody ActiveAccountRequest request) {
        return userService.activeAccount(request);
    }

    @PostMapping("/login")
    private LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/forgotPassword")
    private ResponseEntity forgotPassword(@RequestParam String email){
        return userService.forgotPassword(email);
    }

    @PostMapping("/resetPassword")
    private ResponseEntity resetPassword(@RequestBody ActiveAccountRequest request){
        return userService.resetPassword(request);
    }
}
