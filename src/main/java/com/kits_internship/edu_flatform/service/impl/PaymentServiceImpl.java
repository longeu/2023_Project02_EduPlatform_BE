package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.config.DateConfig;
import com.kits_internship.edu_flatform.entity.PaymentEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.base.MetadataResponse;
import com.kits_internship.edu_flatform.model.request.PaymentFilterRequest;
import com.kits_internship.edu_flatform.model.response.PaymentResponse;
import com.kits_internship.edu_flatform.repository.PaymentRepository;
import com.kits_internship.edu_flatform.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl extends BaseServiceImpl<PaymentEntity, PaymentRepository> implements PaymentService {
    public PaymentServiceImpl(PaymentRepository jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DateConfig dateConfig;

    private static final Map<String, Object> errors = new HashMap<>();

    @Override
    public ListResponseModel filter(PaymentFilterRequest filterRequest) {
        Page<PaymentEntity> paymentEntities = jpaRepository.filter(
                filterRequest.getKeyword(),
                filterRequest.getStatus(),
                PageRequest.of(filterRequest.getPage() - 1, filterRequest.getLimit(), Sort.by(Sort.Order.desc("createdDate"))));

        ListResponseModel responses = new ListResponseModel();
        List<PaymentResponse> responseList = paymentEntities.stream().map(paymentEntity -> modelMapper.map(paymentEntity, PaymentResponse.class)).collect(Collectors.toList());
        responses.setResults(responseList);

        MetadataResponse metadata = new MetadataResponse(
                paymentEntities.getTotalElements(),
                filterRequest.getPage(),
                filterRequest.getLimit()
        );
        responses.setMetadata(metadata);
        return responses;
    }

    @Override
    public Optional<PaymentEntity> findPaymentById(Long id) {
        Optional<PaymentEntity> paymentEntity = jpaRepository.findById(id);
        if (paymentEntity.isEmpty()) {
            errors.put("payment", "Not found payment");
            throw new NotFoundException(errors);
        }
        return paymentEntity;
    }

    @Override
    @Transactional
    public PaymentResponse createPayment(PaymentEntity request) {
        Optional<PaymentEntity> existName = jpaRepository.findByName(request.getName());
        if (existName.isPresent()) {
            errors.put("payment", "existed!");
            throw new UnprocessableEntityException(errors);
        }
        PaymentEntity paymentEntity = create(request);
        return modelMapper.map(paymentEntity, PaymentResponse.class);
    }

    @Override
    @Transactional
    public PaymentResponse updatePayment(Long id, PaymentEntity request) {
        Optional<PaymentEntity> existName = findPaymentById(id);
        PaymentEntity paymentEntity = update(existName.get().getId(), request);
        return modelMapper.map(paymentEntity, PaymentResponse.class);
    }


}
