package com.kits_internship.edu_flatform.model;

import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.TransactionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class TransactionDetailModel {
    private String courseName;
    private BigDecimal price;
    private Long courseID;
}
