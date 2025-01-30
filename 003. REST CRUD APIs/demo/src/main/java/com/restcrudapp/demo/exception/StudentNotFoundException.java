package com.restcrudapp.demo.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Throwable cause) {
        super(cause);
    }

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public StudentNotFoundException(String message) {
        super(message);
    }
}
