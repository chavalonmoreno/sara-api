package com.example.sara.exceptions;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BussinessRuleException.class)
    protected ResponseEntity<Object> unauthorized(RuntimeException ex, WebRequest request) {

        BussinessRuleException bussinessRuleException = (BussinessRuleException) ex;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", ex.getMessage());

        return handleExceptionInternal(ex, jsonObject.toString(), new HttpHeaders(), bussinessRuleException.getHttpStatus(), request);
    }
}
