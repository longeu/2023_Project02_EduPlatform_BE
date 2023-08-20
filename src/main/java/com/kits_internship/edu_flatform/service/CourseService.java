package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.CourseFilterRequest;
import com.kits_internship.edu_flatform.model.request.CourseTransactionRequest;
import com.kits_internship.edu_flatform.model.request.CourseRequest;
import com.kits_internship.edu_flatform.model.response.CourseResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Optional;

public interface CourseService extends BaseService<CourseEntity> {

    CourseResponse addByCurrentUser(CourseRequest request, Optional<UserPrinciple> user);

    CourseResponse updateByCurrentUser(Long id, CourseRequest request, Optional<UserPrinciple> user);

    ListResponseModel filterByCurrentUser(CourseFilterRequest request, Optional<UserPrinciple> user);

    Optional<CourseEntity> findByIdAndCurrentUser(Long id, Optional<UserPrinciple> user);

    ResponseEntity uploadFile(MultipartFile file, Optional<UserPrinciple> user);

    CourseResponse coursePay(CourseTransactionRequest request, Principal currentUser);
}
