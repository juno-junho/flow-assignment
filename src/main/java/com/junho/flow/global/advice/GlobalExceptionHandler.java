package com.junho.flow.global.advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        String message = e.getMessage();
        ExceptionCode code = ExceptionCode.from(message);

        return new ErrorResponse(message, code.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String code = ExceptionCode.INVALID_INPUT_VALUE.toString();

        return new ErrorResponse(message, code);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getMessage();
        String code = ExceptionCode.INVALID_INPUT_VALUE.toString();

        return new ErrorResponse(message, code);
    }

}
