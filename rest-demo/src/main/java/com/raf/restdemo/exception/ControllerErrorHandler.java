package com.raf.restdemo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerErrorHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleResourceNotFoundException(CustomException exception) {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setError(exception.getMessage());
        errorDetails.setErrorCode(exception.getErrorCode());
        errorDetails.setTimestamp(Instant.now());

        return new ResponseEntity<>(errorDetails, exception.getHttpStatus());
    }

}
