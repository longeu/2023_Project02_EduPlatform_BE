package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.config.DateConfig;
import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.RoleName;
import com.kits_internship.edu_flatform.entity.TeacherEntity;
import com.kits_internship.edu_flatform.exception.NotFoundException;
import com.kits_internship.edu_flatform.exception.UnauthorizedException;
import com.kits_internship.edu_flatform.exception.UnprocessableEntityException;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.base.MetadataResponse;
import com.kits_internship.edu_flatform.model.request.CourseFilterRequest;
import com.kits_internship.edu_flatform.model.request.CourseRequest;
import com.kits_internship.edu_flatform.model.response.CourseResponse;
import com.kits_internship.edu_flatform.repository.CourseRepository;
import com.kits_internship.edu_flatform.security.UserPrinciple;
import com.kits_internship.edu_flatform.service.CategoryService;
import com.kits_internship.edu_flatform.service.CourseService;
import com.kits_internship.edu_flatform.service.TeacherService;
import com.kits_internship.edu_flatform.ulti.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseServiceImpl extends BaseServiceImpl<CourseEntity, CourseRepository> implements CourseService {
    public CourseServiceImpl(CourseRepository jpaRepository) {
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
    CategoryService categoryService;
    @Autowired
    FileUtils fileUtils;

    private static final Map<String, Object> errors = new HashMap<>();

    @Override
    public ListResponseModel filterByCurrentUser(CourseFilterRequest request, Optional<UserPrinciple> user) {
        List<CourseEntity> emptyList = new ArrayList<>();
        Page<CourseEntity> courseEntities = new PageImpl<>(emptyList);

        try {
            String role = user.get().getAuthorities().stream().findAny().get().getAuthority();
            if (role.equals(String.valueOf(RoleName.ROLE_TEACHER)) && user.get().getTeacherID() != null) {
                courseEntities = courseRepository.filter(
                        request.getStatus(),
                        request.getKeyword(),
                        request.getCategoryID(),
                        user.get().getTeacherID(),
                        null,
                        null,
                        PageRequest.of(request.getPage() - 1, request.getLimit(), Sort.by(Sort.Order.desc("createdDate"))));
            } else if (role.equals(String.valueOf(RoleName.ROLE_STUDENT))) {
                courseEntities = courseRepository.filter(
                        request.getStatus(),
                        request.getKeyword(),
                        request.getCategoryID(),
                        null,
                        request.getRegisted() != null && request.getRegisted().equals(true) ? user.get().getStudentID() : null,
                        request.getRegisted() != null ? request.getRegisted() : null,
                        PageRequest.of(request.getPage() - 1, request.getLimit(), Sort.by(Sort.Order.desc("createdDate"))));
            }

            ListResponseModel responses = new ListResponseModel();
            List<CourseResponse> responseList = courseEntities.stream().map(
                    courseEntity -> {
                        CourseResponse responseCourse = modelMapper.map(courseEntity, CourseResponse.class);
                        responseCourse.setTeacherID(courseEntity.getTeacher().getId());
                        responseCourse.setCategoryID(courseEntity.getCategory().getId());
                        return responseCourse;
                    })
                    .collect(Collectors.toList());
            responses.setResults(responseList);

            MetadataResponse metadata = new MetadataResponse(
                    courseEntities.getTotalElements(),
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
    public CourseResponse addByCurrentUser(CourseRequest request, Optional<UserPrinciple> user) {
        if (!user.get().getAuthorities().stream().findAny().get().getAuthority().equals(String.valueOf(RoleName.ROLE_TEACHER)) || user.get().getTeacherID() == null) {
            throw new UnauthorizedException();
        }
        Optional<TeacherEntity> teacherEntity = Optional.ofNullable(teacherService.findById(user.get().getTeacherID()));
        if (teacherEntity.isEmpty()) {
            errors.put("teacher", "Not found teacher");
            throw new NotFoundException(errors);
        }
        Optional<CategoryEntity> categoryEntity = categoryService.findCategoryId(request.getCategoryID());
        if (categoryEntity.isEmpty()) {
            errors.put("category", "Not found category");
            throw new NotFoundException(errors);
        }
        CourseEntity courseEntity = modelMapper.map(request, CourseEntity.class);

        courseEntity.setCategory(categoryEntity.get());
        courseEntity.setTeacher(teacherEntity.get());
        courseEntity = create(courseEntity);

        CourseResponse courseResponse = modelMapper.map(courseEntity, CourseResponse.class);
        courseResponse.setCategoryID(categoryEntity.get().getId());
        courseResponse.setTeacherID(teacherEntity.get().getId());

        return courseResponse;
    }

    @Override
    public CourseResponse updateByCurrentUser(Long id, CourseRequest request, Optional<UserPrinciple> user) {
        Optional<CourseEntity> optionalCourseEntity = findByIdAndCurrentUser(id, user);
        if (optionalCourseEntity.isEmpty()) {
            errors.put("category", "Not found category");
            throw new NotFoundException(errors);
        }
        CourseEntity courseEntity = modelMapper.map(request, CourseEntity.class);
        courseEntity.setId(optionalCourseEntity.get().getId());
        courseEntity.setModifiedDate(dateConfig.getTimestamp());
        courseRepository.save(courseEntity);

        return modelMapper.map(courseEntity, CourseResponse.class);
    }

    @Override
    public Optional<CourseEntity> findByIdAndCurrentUser(Long id, Optional<UserPrinciple> user) {
        if (!user.get().getAuthorities().stream().findAny().get().getAuthority().equals(String.valueOf(RoleName.ROLE_TEACHER)) || user.get().getTeacherID() == null) {
            throw new UnauthorizedException();
        }
        return courseRepository.findEntityByTeacherID(id, user.get().getTeacherID());
    }

    @Override
    public ResponseEntity uploadFile(MultipartFile multipartFile, Optional<UserPrinciple> user) {
        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(multipartFile);
        fileUtils.validateFiles(multipartFiles);
        Map<String, Object> errors = new HashMap<>();
        try {
            String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();

            File file = fileUtils.convertToFile(multipartFile, fileName);
            String FILE_URL = "";
            if (multipartFile.getContentType().startsWith("video")) {
                FILE_URL = fileUtils.uploadVideo(file, "video/" + fileName);
            }
            if (multipartFile.getContentType().startsWith("image")) {
                FILE_URL = fileUtils.uploadVideo(file, "image/" + fileName);
            }
            file.delete();
            return ResponseEntity.status(HttpStatus.OK).body(FILE_URL);
        } catch (Exception e) {
            log.error(e.getMessage());
            errors.put("errors", e.getMessage());
            errors.put("file", "files upload fail!");
            throw new UnprocessableEntityException(errors);
        }
    }

}
