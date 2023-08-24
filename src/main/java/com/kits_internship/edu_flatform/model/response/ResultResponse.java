package com.kits_internship.edu_flatform.model.response;

import com.kits_internship.edu_flatform.model.ResultDetailModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ResultResponse {
    private Long id;
    private Long lectureID;
    private double totalPoint;
    private List<ResultDetailModel> resultDetailModels;
}
