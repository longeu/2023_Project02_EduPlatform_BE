package com.kits_internship.edu_flatform.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class StudentResponse {
    private Long id;
    private String email;
    private String phone;
    private String image;
    private String firstName;
    private String lastName;
    private String description;

    private Long userID;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDate;

}
