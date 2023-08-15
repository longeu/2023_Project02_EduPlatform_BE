package com.kits_internship.edu_flatform.exception;

public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException() {
        super("Exception");
    }
}
