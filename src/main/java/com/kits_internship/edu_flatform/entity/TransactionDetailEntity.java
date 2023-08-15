package com.kits_internship.edu_flatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transaction_detail")
@RequiredArgsConstructor
public class TransactionDetailEntity extends BaseEntity {
    private String courseName;
    private String price;

    @OneToOne
    @JoinColumn(name = "transactionID")
    private TransactionEntity transaction;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "courseID")
    private CourseEntity course;
}
