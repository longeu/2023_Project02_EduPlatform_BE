package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.UserEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.ActiveAccountRequest;
import com.kits_internship.edu_flatform.model.request.CourseFilterRequest;
import com.kits_internship.edu_flatform.model.request.LoginRequest;
import com.kits_internship.edu_flatform.model.response.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface CourseService extends BaseService<CourseEntity> {

    ListResponseModel filter(CourseFilterRequest request);
}
