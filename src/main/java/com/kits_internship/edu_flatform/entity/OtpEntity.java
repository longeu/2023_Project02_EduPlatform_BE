package com.kits_internship.edu_flatform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "otp")
@RequiredArgsConstructor
public class OtpEntity extends BaseEntity {
    private String email;
    private String opt;
    @Enumerated(EnumType.STRING)
    private OtpType type;
    private Date expiredDate;
 }
