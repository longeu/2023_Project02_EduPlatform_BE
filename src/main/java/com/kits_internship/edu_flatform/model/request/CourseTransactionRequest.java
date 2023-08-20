package com.kits_internship.edu_flatform.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CourseTransactionRequest {
    private Long studentID;
    private Long courseID;
    private Long paymentID;
    private BigDecimal total;
    private List<TransactionDetailModel> transactionDetail;
}
