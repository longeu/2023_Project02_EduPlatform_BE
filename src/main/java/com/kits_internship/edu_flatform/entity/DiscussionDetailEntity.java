package com.kits_internship.edu_flatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "discussion_detail")
@RequiredArgsConstructor
public class DiscussionDetailEntity extends BaseEntity {
    private String comment;

    @ManyToMany
    @JoinTable(
            name = "student_discussion_detail",
            joinColumns = @JoinColumn(name = "discussionDetailID"),
            inverseJoinColumns = @JoinColumn(name = "studentID")
    )
    private List<StudentEntity> students = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "teacherID")
    private TeacherEntity teacher;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "discussionID")
    private DiscussionEntity discussion;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDate;
}
