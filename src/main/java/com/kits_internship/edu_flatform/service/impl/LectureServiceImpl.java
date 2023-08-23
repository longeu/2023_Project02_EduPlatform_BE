package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.config.DateConfig;
import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.LectureEntity;
import com.kits_internship.edu_flatform.entity.RoleName;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnauthorizedException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.base.MetadataResponse;
import com.kits_internship.edu_flatform.model.request.LectureFilterRequest;
import com.kits_internship.edu_flatform.model.request.LectureRequest;
import com.kits_internship.edu_flatform.model.response.LectureResponse;
import com.kits_internship.edu_flatform.repository.CourseRepository;
import com.kits_internship.edu_flatform.repository.LectureRepository;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.LectureService;
import com.kits_internship.edu_flatform.service.TeacherService;
import com.kits_internship.edu_flatform.ulti.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LectureServiceImpl extends BaseServiceImpl<LectureEntity, LectureRepository> implements LectureService {
    public LectureServiceImpl(LectureRepository jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DateConfig dateConfig;
    @Autowired
    TeacherService teacherService;
    @Autowired
    FileUtils fileUtils;

    @Override
    public ListResponseModel filterByCurrentUser(LectureFilterRequest request, Optional<UserPrinciple> user) {
        try {
            Page<LectureEntity> lectureEntities = jpaRepository.filter(
                    request.getStatus(),
                    request.getKeyword(),
                    request.getCourseID(),
                    PageRequest.of(request.getPage() - 1, request.getLimit(), Sort.by(Sort.Order.desc("createdDate"))));

            ListResponseModel responses = new ListResponseModel();
            List<LectureResponse> responseList = lectureEntities.stream().map(
                    lectureEntity -> {
                        LectureResponse lectureResponse = modelMapper.map(lectureEntity, LectureResponse.class);
                        lectureResponse.setCourseID(lectureEntity.getCourse().getId());
                        return lectureResponse;
                    }).collect(Collectors.toList());
            responses.setResults(responseList);

            MetadataResponse metadata = new MetadataResponse(
                    lectureEntities.getTotalElements(),
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
    public LectureResponse addByCurrentUser(LectureRequest request, Optional<UserPrinciple> user) {
        if (!user.get().getAuthorities().stream().findAny().get().getAuthority().equals(String.valueOf(RoleName.ROLE_TEACHER)) || user.get().getTeacherID() == null) {
            throw new UnauthorizedException();
        }

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(request.getCourseID());
        try {
            CourseEntity courseEntity = courseEntityOptional.orElseThrow();
            LectureEntity lectureEntity = modelMapper.map(request, LectureEntity.class);
            lectureEntity.setCreatedDate(dateConfig.getTimestamp());
            lectureEntity.setModifiedDate(dateConfig.getTimestamp());
            lectureEntity.setCourse(courseEntity);
            lectureEntity = jpaRepository.save(lectureEntity);
            return modelMapper.map(lectureEntity, LectureResponse.class);
        } catch (Exception e) {
            errors.put("errors", e.getMessage());
            errors.put("lecture", "Add not success!");
            throw new UnprocessableEntityException(errors);
        }
    }

    @Override
    public LectureResponse updateByCurrentUser(Long id, LectureRequest request, Optional<UserPrinciple> user) {
        Optional<LectureEntity> optionalLectureEntity = findByIdAndCurrentUser(id, request.getCourseID(), user);
        if (optionalLectureEntity.isEmpty()) {
            errors.put("lecture", "Not found lecture");
            throw new NotFoundException(errors);
        }
        LectureEntity lectureEntity = optionalLectureEntity.get();
        lectureEntity.setDescription(request.getDescription());
        lectureEntity.setName(request.getName());
        lectureEntity.setDocumentPath(request.getDocumentPath());
        lectureEntity.setVideoPath(request.getVideoPath());
        lectureEntity.setStatus(request.getStatus());
        lectureEntity.setModifiedDate(dateConfig.getTimestamp());
        jpaRepository.save(lectureEntity);

        return modelMapper.map(lectureEntity, LectureResponse.class);
    }

    @Override
    public Optional<LectureEntity> findByIdAndCurrentUser(Long id, Long courseID, Optional<UserPrinciple> user) {
        if (!user.get().getAuthorities().stream().findAny().get().getAuthority().equals(String.valueOf(RoleName.ROLE_TEACHER)) || user.get().getTeacherID() == null) {
            throw new UnauthorizedException();
        }
        return jpaRepository.findByIdAndCourse(id, courseID);
    }
}
