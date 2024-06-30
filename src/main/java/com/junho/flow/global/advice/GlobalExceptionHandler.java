package com.junho.flow.global.advice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 입력값에 대한 예외처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        String message = e.getMessage();
        ExceptionCode code = ExceptionCode.from(message);

        return new ErrorResponse(message, code.toString());
    }

    /**
     * 파일 시그니처 값 검증 실패에 대한 예외 처리
     */
    @ExceptionHandler(SecurityException.class)
    public ErrorResponse handleSecurityException(SecurityException e) {
        String message = e.getMessage();
        ExceptionCode code = ExceptionCode.from(message);

        return new ErrorResponse(message, code.toString());
    }

    /**
     * 컨트롤러단 검증 실패에 대한 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String code = ExceptionCode.INVALID_INPUT_VALUE.toString();

        return new ErrorResponse(message, code);
    }

    /**
     * 컨트롤러단 검증 실패에 대한 예외 처리
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getMessage();
        String code = ExceptionCode.INVALID_INPUT_VALUE.toString();

        return new ErrorResponse(message, code);
    }

}
