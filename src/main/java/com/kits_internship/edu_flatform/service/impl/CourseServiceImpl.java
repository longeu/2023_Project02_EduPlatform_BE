package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.entity.CategoryEntity;
import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.model.base.ListResponseModel;
import com.kits_internship.edu_flatform.model.base.MetadataResponse;
import com.kits_internship.edu_flatform.model.request.CourseFilterRequest;
import com.kits_internship.edu_flatform.model.response.CategoryResponse;
import com.kits_internship.edu_flatform.model.response.CourseResponse;
import com.kits_internship.edu_flatform.repository.CategoryRepository;
import com.kits_internship.edu_flatform.repository.CourseRepository;
import com.kits_internship.edu_flatform.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl extends BaseServiceImpl<CourseEntity, CourseRepository> implements CourseService {
    public CourseServiceImpl(CourseRepository jpaRepository) {
        super(jpaRepository);
    }

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ListResponseModel filter(CourseFilterRequest request) {
        Page<CourseEntity> courseEntities = courseRepository.filter(
                request.getStatus(),
                request.getName(),
                PageRequest.of(request.getPage() - 1, request.getLimit(), Sort.by(Sort.Order.desc("createdDate"))));

        ListResponseModel responses = new ListResponseModel();
        List<CourseResponse> responseList = courseEntities.stream().map(courseEntity -> modelMapper.map(courseEntity, CourseResponse.class)).collect(Collectors.toList());
        responses.setResults(responseList);

        MetadataResponse metadata = new MetadataResponse(
                courseEntities.getTotalElements(),
                request.getPage(),
                request.getLimit()
        );
        responses.setMetadata(metadata);
        return responses;
    }


}
