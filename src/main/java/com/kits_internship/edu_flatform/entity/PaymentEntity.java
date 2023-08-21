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
@Table(name = "payment")
@RequiredArgsConstructor
public class PaymentEntity extends BaseEntity {
    private String name;
    private String image;
    @Enumerated(value = EnumType.STRING)
    private StatusName status;

    @OneToMany(mappedBy = "payment")
    private List<TransactionEntity> transactions = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")

    private Date modifiedDate;
}
