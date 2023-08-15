package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.model.request.ActiveAccountRequest;
import com.kits_internship.edu_flatform.model.request.LoginRequest;
import com.kits_internship.edu_flatform.model.response.ActiveAccountResponse;
import com.kits_internship.edu_flatform.model.response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface UserService extends BaseService<UserEntity> {
    UserEntity createAccount(UserEntity userEntity);

    UserEntity findByEmail(String email);

    ResponseEntity activeAccount(ActiveAccountRequest activeAccountRequest);

    LoginResponse login(LoginRequest request);
}
