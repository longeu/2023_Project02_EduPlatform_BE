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
@RequiredArgsConstructor
@Entity
@Table(name = "teacher")
public class TeacherEntity extends BaseEntity {
    @Column(unique = true)
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String image;
    private String bio;
    private String link;
    private List<String> certificates;
    private String experience;

    @OneToOne
    @JoinColumn(name = "userID")
    private UserEntity user;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")

    private Date modifiedDate;

    @OneToMany(mappedBy = "teacher")
    private List<CourseEntity> courses = new ArrayList<>();

    @OneToMany(mappedBy = "teacher")
    private List<DiscussionDetailEntity> discussion = new ArrayList<>();
}
