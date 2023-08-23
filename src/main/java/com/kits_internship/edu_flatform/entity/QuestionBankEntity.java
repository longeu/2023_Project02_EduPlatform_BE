package com.kits_internship.edu_flatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "question_bank")
@Getter
@Setter
@RequiredArgsConstructor
public class QuestionBankEntity extends BaseEntity {
    private String name;
    private String type;
    private String content;
    private List<String> options;
    private String correctOption;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")

    private Date modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "lectureID")
    private LectureEntity lecture;

}
