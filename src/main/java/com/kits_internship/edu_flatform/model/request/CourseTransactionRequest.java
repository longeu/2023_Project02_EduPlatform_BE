package com.kits_internship.edu_flatform.model.request;

import com.kits_internship.edu_flatform.model.TransactionDetailModel;
import com.kits_internship.edu_flatform.model.response.PaymentResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class CourseTransactionRequest {
    private Long paymentID;
    private String paymentName;
    private BigDecimal total;
    private List<TransactionDetailModel> transactionDetails;
}
