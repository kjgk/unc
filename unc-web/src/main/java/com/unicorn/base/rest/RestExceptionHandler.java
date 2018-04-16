package com.unicorn.base.rest;

import com.alibaba.fastjson.JSON;
import com.unicorn.base.MediaTypes;
import com.unicorn.core.exception.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServiceException.class})
    protected ResponseEntity<Object> handleExceptionInternal(ServiceException ex, WebRequest request) {

        ex.printStackTrace();
        Map errors = new HashMap();
        errors.put("error", ex.getMessage());
        errors.put("code", ex.getCode());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaTypes.TEXT_PLAIN_UTF_8));
        return handleExceptionInternal(ex, JSON.toJSON(errors).toString(), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, WebRequest request) {

        ex.printStackTrace();
        Map errors = new HashMap();
        if (ex instanceof FileNotFoundException) {
            errors.put("error", "File Not Found!");
        } else {
            errors.put("error", "HTTP-Internal Server Error!");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaTypes.TEXT_PLAIN_UTF_8));
        return handleExceptionInternal(ex, JSON.toJSON(errors).toString(), headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
