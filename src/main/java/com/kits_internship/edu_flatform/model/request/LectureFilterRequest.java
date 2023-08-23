package com.kits_internship.edu_flatform.model.request;

import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.model.base.BasePagingQueryRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureFilterRequest extends BasePagingQueryRequest {
    private Long courseID;
    private StatusName status;
}