package com.kits_internship.edu_flatform.model.response;

import com.kits_internship.edu_flatform.entity.StatusName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentResponse {
    private String name;
    private String image;
    private StatusName status;
}
