package com.kits_internship.edu_flatform.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kits_internship.edu_flatform.entity.RoleName;
import com.kits_internship.edu_flatform.entity.StatusName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class UserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private StatusName status;
    private RoleName role;
}
