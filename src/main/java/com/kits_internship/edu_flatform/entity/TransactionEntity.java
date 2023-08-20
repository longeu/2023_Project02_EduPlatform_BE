package com.kits_internship.edu_flatform.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "transaction")
@RequiredArgsConstructor
public class TransactionEntity extends BaseEntity {
    private String transactionName;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "studentID")
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "paymentID")
    private PaymentEntity payment;

    private String paymentName;

    private BigDecimal total;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @OneToOne(mappedBy = "transaction")
    private TransactionDetailEntity transactionDetail;
}