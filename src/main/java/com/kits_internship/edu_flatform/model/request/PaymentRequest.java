package com.kits_internship.edu_flatform.model.request;

import com.kits_internship.edu_flatform.entity.StatusName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PaymentRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String image;
    @NotNull
    private StatusName status;
}
