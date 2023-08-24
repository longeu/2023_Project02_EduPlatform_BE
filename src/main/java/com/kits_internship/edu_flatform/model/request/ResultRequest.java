package com.kits_internship.edu_flatform.model.request;

import com.kits_internship.edu_flatform.model.ResultDetailModel;
import com.kits_internship.edu_flatform.model.TransactionDetailModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ResultRequest {
    private Long lectureID;
    private double totalPoint;
    private List<ResultDetailModel> resultDetailModels;
}
