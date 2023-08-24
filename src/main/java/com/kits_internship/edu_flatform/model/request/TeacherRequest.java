package com.kits_internship.edu_flatform.model.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TeacherRequest {
    private String phone;
    private String firstName;
    private String lastName;
    private String image;
    private String bio;
    private String link;
    private List<String> certificates;
    private String experience;
}
