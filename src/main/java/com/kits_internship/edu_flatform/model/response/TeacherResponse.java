package com.kits_internship.edu_flatform.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TeacherResponse {
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String image;
    private String bio;
    private String link;
    private List<String> certificates;
    private String experience;
    private Long userID;
    private Date createdDate;
    private Date modifiedDate;

}
