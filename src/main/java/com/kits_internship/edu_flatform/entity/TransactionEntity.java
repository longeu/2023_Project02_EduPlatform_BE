package com.kits_internship.edu_flatform.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "transaction")
@RequiredArgsConstructor
public class TransactionEntity extends BaseEntity {
    private String transactionName;
    @Enumerated(EnumType.STRING)
    private StatusTransaction status;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "studentID")
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "paymentID")
    private PaymentEntity payment;

    private String paymentName;

    private BigDecimal total;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @OneToMany(mappedBy = "transaction")
    private List<TransactionDetailEntity> transactionDetails;
}