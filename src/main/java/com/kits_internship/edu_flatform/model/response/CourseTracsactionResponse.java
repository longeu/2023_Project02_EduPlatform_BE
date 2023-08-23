package com.kits_internship.edu_flatform.model.response;

import com.kits_internship.edu_flatform.entity.StatusTransaction;
import com.kits_internship.edu_flatform.model.TransactionDetailModel;
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
    private Long paymentID;
    private String paymentName;
    private BigDecimal total;
    private StatusTransaction status;
    private List<TransactionDetailModel> transactionDetails;
}
