package com.kits_internship.edu_flatform.model.request;

import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.model.base.BasePagingQueryRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseFilterRequest extends BasePagingQueryRequest {
    private String keyword;
    private Long categoryID;
    private StatusName status;
    private boolean registed;
}