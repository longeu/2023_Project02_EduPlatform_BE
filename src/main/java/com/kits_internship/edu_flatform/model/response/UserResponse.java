package com.kits_internship.edu_flatform.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kits_internship.edu_flatform.entity.RoleName;
import com.kits_internship.edu_flatform.entity.StatusName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private StatusName status;
    private RoleName role;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDate;
}
