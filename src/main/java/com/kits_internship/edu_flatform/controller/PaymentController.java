package com.kits_internship.edu_flatform.controller;

import com.kits_internship.edu_flatform.entity.PaymentEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.request.PaymentFilterRequest;
import com.kits_internship.edu_flatform.model.request.PaymentRequest;
import com.kits_internship.edu_flatform.model.response.PaymentResponse;
import com.kits_internship.edu_flatform.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/add")
    private PaymentResponse addPayment(@RequestBody PaymentRequest paymentRequest) {
        PaymentEntity paymentEntity = modelMapper.map(paymentRequest, PaymentEntity.class);
        return paymentService.createPayment(paymentEntity);
    }

    @PutMapping("/update/{id}")
    private PaymentResponse updatePayment(@RequestBody PaymentRequest paymentRequest, @PathVariable Long id) {
        PaymentEntity paymentEntity = modelMapper.map(paymentRequest, PaymentEntity.class);
        return paymentService.updatePayment(id, paymentEntity);
    }

    @GetMapping("/list")
    private ListResponseModel listPayment(PaymentFilterRequest filterRequest) {
        return paymentService.filter(filterRequest);
    }

    @GetMapping("/{id}")
    private PaymentResponse getById(@PathVariable Long id) {
        Optional<PaymentEntity> paymentEntity = paymentService.findPaymentById(id);
        return modelMapper.map(paymentEntity, PaymentResponse.class);
    }
}
