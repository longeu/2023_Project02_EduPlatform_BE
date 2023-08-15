package com.kits_internship.edu_flatform.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank
    @Size(max = 30)
    private String username;
    @NotBlank
    @Size(max = 30)
    private String password;
}
