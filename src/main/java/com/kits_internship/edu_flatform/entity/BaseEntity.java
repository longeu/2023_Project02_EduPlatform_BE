package com.kits_internship.edu_flatform.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//    private Date createdAt;
//    @Temporal(TemporalType.TIMESTAMP)
//    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
//    private Date updatedAt;
}
