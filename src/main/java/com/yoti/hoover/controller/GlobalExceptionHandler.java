package com.yoti.hoover.controller;

import com.yoti.hoover.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static ApiError getBuild(FieldError error) {
        return ApiError.builder()
                .code(error.getField())
                .message(error.getDefaultMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {

        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        final List<ApiError> errors = fieldErrors.stream()
                .map(GlobalExceptionHandler::getBuild)
                .collect(Collectors.toList());

        return new ResponseEntity(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(final HttpMessageNotReadableException exception) {

        log.error("Not readable exception : {} ", exception.getMessage());
        return new ResponseEntity<>(Map.of("error", "Invalid input"), HttpStatus.BAD_REQUEST);
    }
}
