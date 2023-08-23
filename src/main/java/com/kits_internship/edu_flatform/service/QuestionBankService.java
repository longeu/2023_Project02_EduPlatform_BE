package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.LectureEntity;
import com.kits_internship.edu_flatform.entity.QuestionBankEntity;
import com.kits_internship.edu_flatform.model.base.BasePagingQueryRequest;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.LectureFilterRequest;
import com.kits_internship.edu_flatform.model.request.LectureRequest;
import com.kits_internship.edu_flatform.model.request.QuestionBankFilterRequest;
import com.kits_internship.edu_flatform.model.request.QuestionBankRequest;
import com.kits_internship.edu_flatform.model.response.LectureResponse;
import com.kits_internship.edu_flatform.model.response.QuestionBankResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;

import java.util.Optional;

public interface QuestionBankService extends BaseService<QuestionBankEntity> {

    QuestionBankResponse addByCurrentUser(QuestionBankRequest request, Optional<UserPrinciple> user);

    QuestionBankResponse updateByCurrentUser(Long id, QuestionBankRequest request, Optional<UserPrinciple> user);

    ListResponseModel filterByCurrentUser(QuestionBankFilterRequest request);

    Optional<QuestionBankEntity> findByIdAndCurrentUser(Long id, Long courseID, Optional<UserPrinciple> user);

}
