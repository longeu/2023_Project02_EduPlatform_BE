package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.LectureEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.LectureFilterRequest;
import com.kits_internship.edu_flatform.model.request.LectureRequest;
import com.kits_internship.edu_flatform.model.response.LectureResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;

import java.util.Optional;

public interface LectureService extends BaseService<LectureEntity> {

    LectureResponse addByCurrentUser(LectureRequest request, Optional<UserPrinciple> user);

    LectureResponse updateByCurrentUser(Long id, LectureRequest request, Optional<UserPrinciple> user);

    ListResponseModel filterByCurrentUser(LectureFilterRequest request);

    Optional<LectureEntity> findByIdAndCurrentUser(Long id, Long courseID, Optional<UserPrinciple> user);

}
