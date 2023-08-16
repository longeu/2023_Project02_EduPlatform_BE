package com.kits_internship.edu_flatform.service.impl;

import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.repository.CourseRepository;
import com.kits_internship.edu_flatform.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends BaseServiceImpl<CourseEntity, CourseRepository> implements CourseService {
    public CourseServiceImpl(CourseRepository jpaRepository) {
        super(jpaRepository);
    }

}
