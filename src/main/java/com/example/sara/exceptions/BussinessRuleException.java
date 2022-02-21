package com.example.sara.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BussinessRuleException extends RuntimeException
{
    private HttpStatus httpStatus;

    public BussinessRuleException(String message) {
        super(message, null);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
