package com.kits_internship.edu_flatform.model.response;

import com.kits_internship.edu_flatform.entity.RoleName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveAccountResponse {
    private String email;
    private String firstName;
    private String lastName;
    private RoleName role;
}
