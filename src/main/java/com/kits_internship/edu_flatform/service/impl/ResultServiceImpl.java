package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.config.DateConfig;
import com.kits_internship.edu_flatform.entity.*;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnauthorizedException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.ResultDetailModel;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.base.MetadataResponse;
import com.kits_internship.edu_flatform.model.request.ResultFilterRequest;
import com.kits_internship.edu_flatform.model.request.ResultRequest;
import com.kits_internship.edu_flatform.model.response.ResultResponse;
import com.kits_internship.edu_flatform.repository.LectureRepository;
import com.kits_internship.edu_flatform.repository.ResultDetailRepository;
import com.kits_internship.edu_flatform.repository.ResultRepository;
import com.kits_internship.edu_flatform.repository.StudentRepository;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.ResultService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl extends BaseServiceImpl<ResultEntity, ResultRepository> implements ResultService {
    public ResultServiceImpl(ResultRepository jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DateConfig dateConfig;
    @Autowired
    ResultDetailRepository resultDetailRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    LectureRepository lectureRepository;

    @Override
    @Transactional
    public ResultResponse resultQuiz(ResultRequest request, Optional<UserPrinciple> currentUser) {
        String role = currentUser.get().getAuthorities().stream().findAny().get().getAuthority();
        if (!role.equals(String.valueOf(RoleName.ROLE_STUDENT)) || currentUser.get().getStudentID() == null) {
            throw new UnauthorizedException();
        }
        Optional<StudentEntity> studentEntity = studentRepository.findById(currentUser.get().getStudentID());
        if (studentEntity.isEmpty()) {
            errors.put("student", "not found");
            throw new NotFoundException(errors);
        }

        try {
            Optional<LectureEntity> lectureEntity = lectureRepository.findById(request.getLectureID());
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setLecture(lectureEntity.orElseThrow());
            resultEntity.setStudent(studentEntity.get());
            resultEntity.setTotalPoint(request.getTotalPoint());
            resultEntity = jpaRepository.save(resultEntity);

            ResultEntity finalEntity = resultEntity;
            List<ResultDetailEntity> resultDetailEntities = request.getResultDetailModels().stream().map(resultDetailModel -> {
                QuestionBankEntity questionBankEntity = modelMapper.map(resultDetailModel.getQuestionBank(), QuestionBankEntity.class);
                ResultDetailEntity resultDetailEntity = modelMapper.map(resultDetailModel, ResultDetailEntity.class);
                resultDetailEntity.setQuestionBank(questionBankEntity);
                resultDetailEntity.setResult(finalEntity);
                return resultDetailEntity;
            }).collect(Collectors.toList());
            resultDetailEntities = resultDetailRepository.saveAll(resultDetailEntities);

            resultEntity.setResultDetails(resultDetailEntities);
            jpaRepository.save(resultEntity);

            return modelMapper.map(resultEntity, ResultResponse.class);
        } catch (Exception e) {
            errors.put("errors", e.getMessage());
            errors.put("result", "Fail to add!");
            throw new UnprocessableEntityException(errors);
        }

    }

    @Override
    public ListResponseModel listResult(ResultFilterRequest request, Optional<UserPrinciple> currentUser) {
        String role = currentUser.get().getAuthorities().stream().findAny().get().getAuthority();

        if (!role.equals(String.valueOf(RoleName.ROLE_STUDENT)) || currentUser.get().getStudentID() == null) {
            throw new UnauthorizedException();
        }

        Page<ResultEntity> resultEntities = jpaRepository.filter(
                request.getLectureID(),
                currentUser.get().getStudentID(),
                PageRequest.of(request.getPage() - 1, request.getLimit(), Sort.by(Sort.Order.desc("createdDate"))));

        ListResponseModel responses = new ListResponseModel();
        List<ResultResponse> responseList = resultEntities.stream().map(resultEntity ->
                {
                    ResultResponse resultResponse = modelMapper.map(resultEntity, ResultResponse.class);
                    List<ResultDetailModel> resultDetailModels = resultEntity.getResultDetails().stream().map(resultDetailEntity -> {
                        ResultDetailModel transactionDetailModel = modelMapper.map(resultDetailEntity, ResultDetailModel.class);
                        transactionDetailModel.setResultID(resultEntity.getId());
                        return transactionDetailModel;
                    }).collect(Collectors.toList());
                    resultResponse.setResultDetailModels(resultDetailModels);
                    return resultResponse;
                }
        ).collect(Collectors.toList());
        responses.setResults(responseList);

        MetadataResponse metadata = new MetadataResponse(
                resultEntities.getTotalElements(),
                request.getPage(),
                request.getLimit()
        );
        responses.setMetadata(metadata);
        return responses;
    }

    @Override
    public ResultResponse getById(Long id, Optional<UserPrinciple> currentUser) {
        String role = currentUser.get().getAuthorities().stream().findAny().get().getAuthority();

        if (!role.equals(String.valueOf(RoleName.ROLE_STUDENT)) || currentUser.get().getStudentID() == null) {
            throw new UnauthorizedException();
        }
        Optional<ResultEntity> resultEntity = jpaRepository.findByIdAndUser(id, currentUser.get().getStudentID());
        try {
            ResultEntity entity = resultEntity.orElseThrow();
            List<ResultDetailModel> detailModels = mapDetailModels(entity);
            ResultResponse response = modelMapper.map(entity, ResultResponse.class);
            response.setResultDetailModels(detailModels);
            response.setLectureID(entity.getLecture().getId());
            return response;

        } catch (Exception e) {
            errors.put("errors", e.getMessage());
            errors.put("result", "Not found result");
            throw new NotFoundException(errors);
        }
    }

    private List<ResultDetailModel> mapDetailModels(ResultEntity resultEntity) {
        return resultEntity.getResultDetails().stream().map(
                resultDetailEntity -> {
                    ResultDetailModel detailModel = modelMapper.map(resultDetailEntity, ResultDetailModel.class);
                    detailModel.setResultID(resultDetailEntity.getResult().getId());
                    return detailModel;
                }).collect(Collectors.toList());
    }
}
