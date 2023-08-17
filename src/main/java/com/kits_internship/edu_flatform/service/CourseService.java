package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.*;
import com.kits_internship.edu_flatform.model.response.CategoryResponse;
import com.kits_internship.edu_flatform.model.response.CourseResponse;
import com.kits_internship.edu_flatform.model.response.LoginResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface CourseService extends BaseService<CourseEntity> {

    CourseResponse addByCurrentUser(CourseRequest request, Optional<UserPrinciple> user);

    CourseResponse updateByCurrentUser(Long id, CourseRequest request, Optional<UserPrinciple> user);

    ListResponseModel filterByCurrentUser(CourseFilterRequest request, Optional<UserPrinciple> user);

    Optional<CourseEntity> findByIdAndCurrentUser(Long id, Optional<UserPrinciple> user);
}
