package com.kits_internship.edu_flatform.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class NotFoundException extends BaseException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Map<String, Object> messageResult;

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Map<String, Object> messageResult) {
        this.messageResult = messageResult;
    }
}
