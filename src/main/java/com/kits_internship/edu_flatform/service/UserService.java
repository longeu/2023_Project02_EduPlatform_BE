package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.model.request.ActiveAccountRequest;
import com.kits_internship.edu_flatform.model.request.LoginRequest;
import com.kits_internship.edu_flatform.model.response.ActiveAccountResponse;
import com.kits_internship.edu_flatform.model.response.LoginResponse;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserEntity createAccount(UserEntity userEntity);

    UserEntity findByEmail(String email);

    ResponseEntity activeAccount(ActiveAccountRequest activeAccountRequest);

    LoginResponse login(LoginRequest request);

    UserEntity findByUsername(String token);

    ResponseEntity forgotPassword(String email);

    ResponseEntity resetPassword(ActiveAccountRequest request);
}
