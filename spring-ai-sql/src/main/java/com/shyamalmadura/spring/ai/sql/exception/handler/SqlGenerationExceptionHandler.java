package com.shyamalmadura.spring.ai.sql.exception.handler;

import com.shyamalmadura.spring.ai.sql.exception.SqlGenerationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class SqlGenerationExceptionHandler {

    @ExceptionHandler(SqlGenerationException.class)
    public ProblemDetail handle(SqlGenerationException ex) {
        log.error(ex.getMessage(), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.EXPECTATION_FAILED, ex.getMessage());
    }

}