package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.config.DateConfig;
import com.kits_internship.edu_flatform.entity.*;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnauthorizedException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.TransactionDetailModel;
import com.kits_internship.edu_flatform.model.base.BasePagingQueryRequest;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.base.MetadataResponse;
import com.kits_internship.edu_flatform.model.request.CourseTransactionRequest;
import com.kits_internship.edu_flatform.model.response.CourseTracsactionResponse;
import com.kits_internship.edu_flatform.repository.*;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<TransactionEntity, TransactionRepository> implements TransactionService {
    public TransactionServiceImpl(TransactionRepository jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DateConfig dateConfig;
    @Autowired
    TransactionDetailRepository transactionDetailRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    PaymentRepository paymentRepository;

    private static final Map<String, Object> errors = new HashMap<>();

    @Override
    @Transactional
    public CourseTracsactionResponse courseTransaction(CourseTransactionRequest request, Optional<UserPrinciple> currentUser) {
        String role = currentUser.get().getAuthorities().stream().findAny().get().getAuthority();
        if (!role.equals(String.valueOf(RoleName.ROLE_STUDENT)) || currentUser.get().getStudentID() == null) {
            throw new UnauthorizedException();
        }
        List<TransactionEntity> existTransactions = jpaRepository.findByStudentID(currentUser.get().getStudentID());

        List<Long> courseIDs = request.getTransactionDetails().stream().map(TransactionDetailModel::getCourseID).collect(Collectors.toList());
        TransactionEntity exitsCourse = existTransactions.stream().filter(transactionEntity -> {
            boolean exitsCoursePayment = transactionEntity.getTransactionDetails()
                    .stream().anyMatch(x -> courseIDs.contains(x.getCourse().getId()));
            return exitsCoursePayment;
        }).findFirst().orElse(null);
        if (exitsCourse != null) {
            errors.put("course", "exist a courses that have been pay");
            throw new UnprocessableEntityException(errors);
        }
        try {
            List<CourseEntity> courseEntities = courseRepository.findByListIds(courseIDs);
            Optional<StudentEntity> studentEntity = studentRepository.findById(currentUser.get().getStudentID());
            Optional<PaymentEntity> paymentEntity = paymentRepository.findById(request.getPaymentID());

            TransactionEntity transactionEntity = modelMapper.map(request, TransactionEntity.class);
            transactionEntity.setTransactionName(String.valueOf(UUID.randomUUID()));
            transactionEntity.setPayment(paymentEntity.orElseThrow());
            transactionEntity.setStudent(studentEntity.orElseThrow());
            transactionEntity.setStatus(StatusTransaction.COMPLETED);
            transactionEntity.setCreatedDate(dateConfig.getTimestamp());
            transactionEntity.setTransactionDetails(new ArrayList<>());

            transactionEntity = jpaRepository.save(transactionEntity);

            TransactionEntity finalTransactionEntity = transactionEntity;


            List<TransactionDetailEntity> detailEntityList = request.getTransactionDetails().stream()
                    .map(transactionDetailModel -> {
                        CourseEntity courseEntity = courseEntities.stream().filter(x -> x.getId().equals(transactionDetailModel.getCourseID())).findFirst().orElseThrow();
                        TransactionDetailEntity entity = modelMapper.map(transactionDetailModel, TransactionDetailEntity.class);
                        entity.setTransaction(finalTransactionEntity);
                        entity.setCourse(courseEntity);
                        return entity;
                    })
                    .collect(Collectors.toList());
            List<TransactionDetailEntity> transactionDetailEntities = transactionDetailRepository.saveAll(detailEntityList);

            transactionEntity.setTransactionDetails(transactionDetailEntities);
            transactionEntity = jpaRepository.save(transactionEntity);

            List<TransactionDetailModel> detailModels = mapDetailModels(transactionEntity);

            CourseTracsactionResponse response = modelMapper.map(transactionEntity, CourseTracsactionResponse.class);
            response.setPaymentID(paymentEntity.orElseThrow().getId());
            response.setTransactionDetails(detailModels);
            return response;
        } catch (Exception e) {
            errors.put("errors", e.getMessage());
            throw new UnprocessableEntityException(errors);
        }
    }

    @Override
    public ListResponseModel listCoursePay(BasePagingQueryRequest request, Optional<UserPrinciple> currentUser) {
        String role = currentUser.get().getAuthorities().stream().findAny().get().getAuthority();

        if (!role.equals(String.valueOf(RoleName.ROLE_STUDENT)) || currentUser.get().getStudentID() == null) {
            throw new UnauthorizedException();
        }

        Page<TransactionEntity> transactionEntities = jpaRepository.filter(
                request.getKeyword(),
                currentUser.get().getStudentID(),
                PageRequest.of(request.getPage() - 1, request.getLimit(), Sort.by(Sort.Order.desc("createdDate"))));

        ListResponseModel responses = new ListResponseModel();
        List<CourseTracsactionResponse> responseList = transactionEntities.stream().map(transactionEntity ->
                {
                    CourseTracsactionResponse tracsactionResponse = modelMapper.map(transactionEntity, CourseTracsactionResponse.class);
                    List<TransactionDetailModel> transactionDetailModels = transactionEntity.getTransactionDetails().stream().map(transactionDetailEntity->{
                        TransactionDetailModel transactionDetailModel = modelMapper.map(transactionDetailEntity,TransactionDetailModel.class);
                        transactionDetailModel.setCourseID(transactionDetailEntity.getCourse().getId());
                        return transactionDetailModel;
                    }).collect(Collectors.toList());
                    tracsactionResponse.setTransactionDetails(transactionDetailModels);
                    return tracsactionResponse;
                }
        ).collect(Collectors.toList());
        responses.setResults(responseList);

        MetadataResponse metadata = new MetadataResponse(
                transactionEntities.getTotalElements(),
                request.getPage(),
                request.getLimit()
        );
        responses.setMetadata(metadata);
        return responses;
    }

    @Override
    public CourseTracsactionResponse getTransactionById(Long id, Optional<UserPrinciple> currentUser) {
        String role = currentUser.get().getAuthorities().stream().findAny().get().getAuthority();

        if (!role.equals(String.valueOf(RoleName.ROLE_STUDENT)) || currentUser.get().getStudentID() == null) {
            throw new UnauthorizedException();
        }
        Optional<TransactionEntity> transactionEntity = jpaRepository.findByIdAndUser(id, currentUser.get().getStudentID());
        try {
            TransactionEntity entity = transactionEntity.orElseThrow();
            List<TransactionDetailModel> detailModels = mapDetailModels(entity);
            CourseTracsactionResponse response = modelMapper.map(entity, CourseTracsactionResponse.class);
            response.setTransactionDetails(detailModels);
            response.setPaymentID(entity.getPayment().getId());
            return response;

        } catch (Exception e) {
            errors.put("errors", e.getMessage());
            errors.put("transaction", "Not found transaction!");
            throw new NotFoundException(errors);
        }
    }

    private List<TransactionDetailModel> mapDetailModels(TransactionEntity transactionEntity) {
        return transactionEntity.getTransactionDetails().stream().map(
                transactionDetailEntity -> {
                    TransactionDetailModel detailModel = modelMapper.map(transactionDetailEntity, TransactionDetailModel.class);
                    detailModel.setCourseID(transactionDetailEntity.getCourse().getId());
                    return detailModel;
                }).collect(Collectors.toList());
    }
}
