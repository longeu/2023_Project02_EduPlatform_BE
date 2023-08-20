package com.kits_internship.edu_flatform.model.response;

import com.kits_internship.edu_flatform.model.request.TransactionDetailModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CourseTracsactionResponse {
    private Long id;
    private String transactionName;
    private Long studentID;
    private Long courseID;
    private Long paymentID;
    private BigDecimal total;
    private List<TransactionDetailModel> transactionDetail;
}
