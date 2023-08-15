package com.kits_internship.edu_flatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "result")
@RequiredArgsConstructor
public class ResultEntity extends BaseEntity{
    @Column(name = "totalPoint")
    private double totalPoint;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "studentID")
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "lectureID")
    private LectureEntity lecture;

    @OneToMany(mappedBy = "result")
    private List<ResultDetailEntity> resultDetails = new ArrayList<>();
}
