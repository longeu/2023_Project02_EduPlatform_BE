package com.kits_internship.edu_flatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.UNAUTHORIZED,
        reason = "UNAUTHORIZED"
)
public class UnauthorizedException extends BaseException {
    public UnauthorizedException() {
    }
}
