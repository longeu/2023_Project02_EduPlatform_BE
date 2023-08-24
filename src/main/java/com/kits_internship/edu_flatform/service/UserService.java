package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.ActiveAccountRequest;
import com.kits_internship.edu_flatform.model.request.LoginRequest;
import com.kits_internship.edu_flatform.model.request.UserFilterRequest;
import com.kits_internship.edu_flatform.model.request.UserRequest;
import com.kits_internship.edu_flatform.model.response.LoginResponse;
import com.kits_internship.edu_flatform.model.response.UserResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService extends BaseService<UserEntity> {
    UserEntity createAccount(UserEntity userEntity, Optional<UserPrinciple> user);

    UserEntity findByEmail(String email);

    ResponseEntity activeAccount(ActiveAccountRequest activeAccountRequest);

    LoginResponse login(LoginRequest request);

    UserEntity findByUsername(String token);

    ResponseEntity forgotPassword(String email);

    ResponseEntity resetPassword(ActiveAccountRequest request);

    ResponseEntity resentOTP(String email);

    ListResponseModel filter(UserFilterRequest request);

    ResponseEntity adminRestUserPassword(String email, String password);

    UserResponse updateUser(UserRequest request, Long id);

    UserResponse changeStatus(StatusName status, Long id);

}
