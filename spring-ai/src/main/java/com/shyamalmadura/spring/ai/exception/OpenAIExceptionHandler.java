package com.shyamalmadura.spring.ai.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import java.util.Optional;

@RestControllerAdvice
public class OpenAIExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    ProblemDetail handleOpenAiHttpException(Exception ex) {
        HttpStatus status = Optional
                .ofNullable(HttpStatus.resolve(500))
                .orElse(HttpStatus.BAD_REQUEST);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle("An Open AI exception has occurred");
        return problemDetail;
    }
}