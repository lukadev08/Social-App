package com.backend.app.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class DataNotFoundException extends Exception{

    public DataNotFoundException() {

    }

    public DataNotFoundException(String message) {
        super(message);
    }
}
