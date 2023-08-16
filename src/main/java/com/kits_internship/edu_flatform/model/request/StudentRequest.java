package com.kits_internship.edu_flatform.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class StudentRequest {
    private String email;
    private String phone;
    private String image;
    private String firstName;
    private String lastName;
    private String description;

}
