package com.kits_internship.edu_flatform.model.request;

import com.kits_internship.edu_flatform.entity.RoleName;
import com.kits_internship.edu_flatform.entity.StatusName;
import com.kits_internship.edu_flatform.model.base.BasePagingQueryRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class UserFilterRequest extends BasePagingQueryRequest {
    private StatusName status;
    private List<RoleName> roles = Arrays.asList(RoleName.ROLE_TEACHER,RoleName.ROLE_STUDENT);
}