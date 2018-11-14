package com.example.postgres.controller;


import com.example.postgres.model.ErrorDetails;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ControllerAdvice()
//@RestControllerAdvice
public class MyControllerAdvice {


   /* @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
                ex.getBindingResult().toString());
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }
*/
   /* @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorDetails handleMyException(Exception ex, WebRequest request) {
        System.out.println("exception controller advice");
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
                "Error occured");
        return errorDetails;
    }*/



    @ExceptionHandler
    public ResponseEntity<ErrorDetails> handle(ConstraintViolationException exception) {
        //you will get all javax failed validation, can be more than one
        //so you can return the set of error messages or just the first message
        String errorMsg = exception.getConstraintViolations().stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .findFirst()
                .orElse(exception.getMessage());
        ErrorDetails apiError = new ErrorDetails(new Date(), "Validation Failed",
                errorMsg);
        return new ResponseEntity<ErrorDetails>(apiError, null, HttpStatus.BAD_REQUEST);
    }



}
