package com.kits_internship.edu_flatform.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "result_detail")
@RequiredArgsConstructor
public class ResultDetailEntity extends BaseEntity {
    private String answer;
    private double point;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "resultID")
    private ResultEntity result;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "questionBankID")
    private QuestionBankEntity questionBank;
}
