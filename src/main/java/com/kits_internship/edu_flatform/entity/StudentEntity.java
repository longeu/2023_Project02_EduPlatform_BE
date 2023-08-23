package com.kits_internship.edu_flatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "student")
public class StudentEntity extends BaseEntity {
    @Column(unique = true)
    private String email;
    private String phone;
    private String image;
    private String firstName;
    private String lastName;
    private String description;

    @OneToOne
    @JoinColumn(name = "userID")
    private UserEntity user;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date modifiedDate;

    @OneToMany(mappedBy = "student")
    private List<ResultEntity> studentResults = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<ReviewEntity> studentReview = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<TransactionEntity> studentTransaction = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<DiscussionEntity> studentDiscussion = new ArrayList<>();

    @ManyToMany(mappedBy = "students")
    private List<DiscussionDetailEntity> discussionDetails = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "studentID"),
            inverseJoinColumns = @JoinColumn(name = "courseID")
    )
    private List<CourseEntity> courses = new ArrayList<>();
}
