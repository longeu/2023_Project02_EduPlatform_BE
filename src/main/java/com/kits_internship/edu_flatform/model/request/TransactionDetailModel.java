package com.kits_internship.edu_flatform.model.request;

import com.kits_internship.edu_flatform.entity.CourseEntity;
import com.kits_internship.edu_flatform.entity.TransactionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TransactionDetailModel {
    private String courseName;
    private String price;
    private Long transactionID;
    private Long courseID;
}
