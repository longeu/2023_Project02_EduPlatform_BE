package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.UserFilterRequest;
import com.kits_internship.edu_flatform.model.request.UserRequest;
import com.kits_internship.edu_flatform.model.response.UserResponse;
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
@RequestMapping("/api/admin")
public class AdminController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/user/list")
    private ListResponseModel listUser(UserFilterRequest request, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        adminCheck(user);
        return userService.filter(request);
    }

    @GetMapping("/user/{id}")
    private UserResponse getById(@PathVariable Long id, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        adminCheck(user);
        UserEntity userEntity = userService.findById(id);
        if (userEntity == null) {
            errors.put("user", "Not found user");
            throw new NotFoundException(errors);
        }
        return modelMapper.map(userEntity, UserResponse.class);
    }

    @PutMapping("/user/resetPassword")
    private ResponseEntity resetPassword(@RequestParam String email, @RequestParam String password, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        adminCheck(user);
        return userService.adminRestUserPassword(email, password);
    }

    @PutMapping("/user/update/{id}")
    private UserResponse updateUser(@RequestBody UserRequest request, @PathVariable Long id, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        adminCheck(user);
        return userService.updateUser(request, id);
    }

    @PutMapping("/user/changeStatus/{id}")
    private UserResponse changeStatus(@RequestParam StatusName status, @PathVariable Long id, Principal currentUser) {
        Optional<UserPrinciple> user = getJwtUser(currentUser);
        if (user.isEmpty()) {
            errors.put("base", "can't identify user");
            throw new NotFoundException(errors);
        }
        adminCheck(user);
        return userService.changeStatus(status, id);
    }
}
