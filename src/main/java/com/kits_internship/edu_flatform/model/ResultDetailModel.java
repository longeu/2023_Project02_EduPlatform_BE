package com.kits_internship.edu_flatform.model;

import com.kits_internship.edu_flatform.entity.QuestionBankEntity;
import com.kits_internship.edu_flatform.entity.ResultEntity;
import com.kits_internship.edu_flatform.model.response.QuestionBankResponse;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@RequiredArgsConstructor
public class ResultDetailModel {
    private String answer;
    private double point;
    private Long resultID;
    private QuestionBankResponse questionBank;
}
