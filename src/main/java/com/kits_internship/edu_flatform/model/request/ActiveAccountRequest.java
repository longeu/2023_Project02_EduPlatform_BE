package com.kits_internship.edu_flatform.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveAccountRequest {
    @Email
    @NotBlank
    private String email;
    @Size(max = 6)
    @NotBlank
    private String opt;
    @NotBlank
    private String password;
}
