package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.config.DateConfig;
import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.LectureEntity;
import com.kits_internship.edu_flatform.entity.QuestionBankEntity;
import com.kits_internship.edu_flatform.entity.RoleName;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnauthorizedException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.base.BasePagingQueryRequest;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.base.MetadataResponse;
import com.kits_internship.edu_flatform.model.request.QuestionBankFilterRequest;
import com.kits_internship.edu_flatform.model.request.QuestionBankRequest;
import com.kits_internship.edu_flatform.model.response.QuestionBankResponse;
import com.kits_internship.edu_flatform.repository.CourseRepository;
import com.kits_internship.edu_flatform.repository.LectureRepository;
import com.kits_internship.edu_flatform.repository.QuestionBankRepository;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.QuestionBankService;
import com.kits_internship.edu_flatform.ulti.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QuestionBankServiceImpl extends BaseServiceImpl<QuestionBankEntity, QuestionBankRepository> implements QuestionBankService {
    public QuestionBankServiceImpl(QuestionBankRepository jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DateConfig dateConfig;
    @Autowired
    QuestionBankRepository questionBankRepository;
    @Autowired
    FileUtils fileUtils;

    @Override
    public ListResponseModel filterByCurrentUser(QuestionBankFilterRequest request) {
        try {
            Page<QuestionBankEntity> questionBankEntities = jpaRepository.filter(
                    request.getKeyword(),
                    request.getLectureID(),
                    PageRequest.of(request.getPage() - 1, request.getLimit(), Sort.by(Sort.Order.desc("createdDate"))));

            ListResponseModel responses = new ListResponseModel();
            List<QuestionBankResponse> responseList = questionBankEntities.stream().map(
                    questionBankEntity -> {
                        QuestionBankResponse questionBankResponse = modelMapper.map(questionBankEntity, QuestionBankResponse.class);
                        questionBankResponse.setLectureID(questionBankEntity.getLecture().getId());
                        return questionBankResponse;
                    }).collect(Collectors.toList());
            responses.setResults(responseList);

            MetadataResponse metadata = new MetadataResponse(
                    questionBankEntities.getTotalElements(),
                    request.getPage(),
                    request.getLimit()
            );
            responses.setMetadata(metadata);
            return responses;
        } catch (Exception e) {
            log.error(e.getMessage());
            errors.put("error", e.getMessage());
            throw new UnprocessableEntityException(errors);
        }
    }

    @Override
    public QuestionBankResponse addByCurrentUser(QuestionBankRequest request, Optional<UserPrinciple> user) {
        if (!user.get().getAuthorities().stream().findAny().get().getAuthority().equals(String.valueOf(RoleName.ROLE_TEACHER)) || user.get().getTeacherID() == null) {
            throw new UnauthorizedException();
        }

        Optional<LectureEntity> lectureEntityOptional = lectureRepository.findById(request.getLectureID());
        try {
            LectureEntity lectureEntity = lectureEntityOptional.orElseThrow();
            QuestionBankEntity questionBankEntity = modelMapper.map(request, QuestionBankEntity.class);
            questionBankEntity.setCreatedDate(dateConfig.getTimestamp());
            questionBankEntity.setModifiedDate(dateConfig.getTimestamp());
            questionBankEntity.setLecture(lectureEntity);
            questionBankEntity = jpaRepository.save(questionBankEntity);
            return modelMapper.map(questionBankEntity, QuestionBankResponse.class);
        } catch (Exception e) {
            errors.put("errors", e.getMessage());
            errors.put("questionBank", "Add not success!");
            throw new UnprocessableEntityException(errors);
        }
    }

    @Override
    public QuestionBankResponse updateByCurrentUser(Long id, QuestionBankRequest request, Optional<UserPrinciple> user) {
        Optional<QuestionBankEntity> optionalQuestionBankEntity = findByIdAndCurrentUser(id, request.getLectureID(), user);
        if (optionalQuestionBankEntity.isEmpty()) {
            errors.put("questionBank", "Not found questionBank");
            throw new NotFoundException(errors);
        }
        QuestionBankEntity questionBankEntity = optionalQuestionBankEntity.get();
        questionBankEntity.setContent(request.getContent());
        questionBankEntity.setName(request.getName());
        questionBankEntity.setOptions(request.getOptions());
        questionBankEntity.setCorrectOption(request.getCorrectOption());
        questionBankEntity.setType(request.getType());
        questionBankEntity.setModifiedDate(dateConfig.getTimestamp());
        jpaRepository.save(questionBankEntity);

        return modelMapper.map(questionBankEntity, QuestionBankResponse.class);
    }

    @Override
    public Optional<QuestionBankEntity> findByIdAndCurrentUser(Long id, Long lectureID, Optional<UserPrinciple> user) {
        if (!user.get().getAuthorities().stream().findAny().get().getAuthority().equals(String.valueOf(RoleName.ROLE_TEACHER)) || user.get().getTeacherID() == null) {
            throw new UnauthorizedException();
        }
        return jpaRepository.findByIdAndLectureID(id, lectureID);
    }
}
