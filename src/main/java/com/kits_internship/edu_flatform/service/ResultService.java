package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.ResultEntity;
import com.kits_internship.edu_flatform.entity.TransactionEntity;
import com.kits_internship.edu_flatform.model.base.BasePagingQueryRequest;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.CourseTransactionRequest;
import com.kits_internship.edu_flatform.model.request.ResultFilterRequest;
import com.kits_internship.edu_flatform.model.request.ResultRequest;
import com.kits_internship.edu_flatform.model.response.CourseTracsactionResponse;
import com.kits_internship.edu_flatform.model.response.ResultResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;

import java.util.Optional;

public interface ResultService extends BaseService<ResultEntity> {

    ResultResponse resultQuiz(ResultRequest request, Optional<UserPrinciple> user);

    ListResponseModel listResult(ResultFilterRequest request, Optional<UserPrinciple> user);

    ResultResponse getById(Long id, Optional<UserPrinciple> user);
}
