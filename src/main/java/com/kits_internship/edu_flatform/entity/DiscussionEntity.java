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
@Table(name = "discussion")
@RequiredArgsConstructor
public class DiscussionEntity extends BaseEntity {
    private String question;
    private String answer;

    @Column(name = "createdDate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @Column(name = "modifiedDate")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "studentID")
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "courseID")
    private CourseEntity course;

    @OneToMany(mappedBy = "discussion")
    private List<DiscussionDetailEntity> detailEntityList = new ArrayList<>();
}