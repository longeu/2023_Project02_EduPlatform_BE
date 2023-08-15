package com.kits_internship.edu_flatform.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lectures")
@RequiredArgsConstructor
public class LectureEntity extends BaseEntity {
    private String description;
    private String name;
    private String documentPath;
    private String videoPath;
    @Enumerated(value = EnumType.STRING)
    private StatusName status;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")

    private Date modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "courseID")
    private CourseEntity course;

    @OneToMany(mappedBy = "lecture")
    private List<QuestionBankEntity> questionBanks = new ArrayList<>();

}