package com.kits_internship.edu_flatform.service;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.entity.PaymentEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.PaymentFilterRequest;
import com.kits_internship.edu_flatform.model.request.PaymentRequest;
import com.kits_internship.edu_flatform.model.response.PaymentResponse;

import java.util.Optional;

public interface PaymentService extends BaseService<PaymentEntity> {

    ListResponseModel filter(PaymentFilterRequest filterRequest);

    Optional<PaymentEntity> findPaymentById(Long id);

    PaymentResponse createPayment(PaymentEntity paymentEntity);

    PaymentResponse updatePayment(Long id, PaymentEntity paymentEntity);
}
