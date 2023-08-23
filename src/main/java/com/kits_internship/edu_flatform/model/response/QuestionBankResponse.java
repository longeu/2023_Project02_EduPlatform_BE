package com.kits_internship.edu_flatform.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionBankResponse {
    private Long id;
    private String name;
    private String type;
    private String content;
    private List<String> options;
    private String correctOption;

    private Long lectureID;
}
