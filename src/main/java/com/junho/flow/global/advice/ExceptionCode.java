package com.junho.flow.global.advice;

import java.util.Arrays;

public enum ExceptionCode {
    EXTENSION_ALREADY_EXISTS("이미 존재하는 확장자입니다."),
    EXTENSION_NOT_FOUND("존재하지 않는 확장자입니다."),
    EXTENSION_NOT_ALLOWED("허용되지 않는 확장자입니다."),
    EXTENSION_NULL_OR_EMPTY("확장자가 비어있거나 null입니다."),
    EXTENSION_MAX_LENGTH("파일의 확장자가 최대 길이를 초과했습니다."),
    EXTENSION_CONTAINS_SPACE("파일의 확장자에 공백이 포함될 수 없습니다."),

    EXTENSION_VALIDATION_FAILED("확장자 검증에 실패했습니다."),
    FILE_UPLOAD_FAILED("파일 업로드에 실패했습니다."),

    INVALID_INPUT_VALUE("유효하지 않은 입력 값입니다."),
    ;

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ExceptionCode from(String message) {
        return Arrays.stream(values())
                .filter(exceptionCode -> exceptionCode.message.equals(message))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ExceptionCode입니다."));
    }
}
