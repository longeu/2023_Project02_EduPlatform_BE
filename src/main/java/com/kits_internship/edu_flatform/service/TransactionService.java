package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.PaymentEntity;
import com.kits_internship.edu_flatform.entity.TransactionEntity;
import com.kits_internship.edu_flatform.model.base.BasePagingQueryRequest;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.CourseTransactionRequest;
import com.kits_internship.edu_flatform.model.request.PaymentFilterRequest;
import com.kits_internship.edu_flatform.model.response.CourseResponse;
import com.kits_internship.edu_flatform.model.response.CourseTracsactionResponse;
import com.kits_internship.edu_flatform.model.response.PaymentResponse;
import com.kits_internship.edu_flatform.security.UserPrinciple;

import java.security.Principal;
import java.util.Optional;

public interface TransactionService extends BaseService<TransactionEntity> {

    CourseTracsactionResponse courseTransaction(CourseTransactionRequest request, Optional<UserPrinciple> user);

    ListResponseModel listCoursePay(BasePagingQueryRequest request, Optional<UserPrinciple> user);

    CourseTracsactionResponse getTransactionById(Long id, Optional<UserPrinciple> user);
}
