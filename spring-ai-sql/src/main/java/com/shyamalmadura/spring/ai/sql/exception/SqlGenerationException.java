package com.shyamalmadura.spring.ai.sql.exception;

public class SqlGenerationException extends RuntimeException {
    public SqlGenerationException(String response) {
        super(response);
    }
}