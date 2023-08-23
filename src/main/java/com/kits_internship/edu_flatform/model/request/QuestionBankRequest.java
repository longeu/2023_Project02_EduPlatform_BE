package com.kits_internship.edu_flatform.model.request;

import com.kits_internship.edu_flatform.entity.LectureEntity;
import com.kits_internship.edu_flatform.entity.StatusName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class QuestionBankRequest {
    private String name;
    private String type;
    private String content;
    private List<String> options;
    private String correctOption;

    private Long lectureID;
}