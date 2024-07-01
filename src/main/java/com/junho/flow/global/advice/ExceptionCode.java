package com.junho.flow.global.advice;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ExceptionCode {
    EXTENSION_ALREADY_EXISTS("이미 존재하는 확장자입니다."),
    EXTENSION_NOT_FOUND("존재하지 않는 확장자입니다."),
    EXTENSION_NOT_ALLOWED("허용되지 않는 확장자입니다."),
    EXTENSION_NULL_OR_EMPTY("확장자가 비어있거나 null입니다."),
    EXTENSION_MAX_LENGTH("파일의 확장자가 최대 길이를 초과했습니다."),
    EXTENSION_CONTAINS_SPACE("파일의 확장자에 공백이 포함되어 있습니다."),
    EXTENSION_NOT_ALPHANUMERIC("파일의 확장자는 영어와 숫자조합만 등록 가능합니다."),
    CUSTOM_EXTENSION_COUNT_EXCEEDED("사용자의 커스텀 확장자 등록 개수를 초과했습니다."),

    // 차단 확장자 및 파일 시그니처 검증
    EXTENSION_BLOCKED("차단된 확장자입니다."),
    FILE_NAME_NOT_FOUND("파일 이름이 존재하지 않습니다."),
    EXTENSION_VALIDATION_FAILED("확장자 검증에 실패했습니다."),
    FILE_UPLOAD_FAILED("파일 업로드에 실패했습니다."),
    FAILED_TO_READ_FILE("파일을 읽는데 실패했습니다."),
    FILE_TOO_LARGE("파일이 너무 큽니다."),

    // etc
    INVALID_INPUT_VALUE("유효하지 않은 입력 값입니다."),
    ;

    private final String message;

    ExceptionCode(String message) {
        this.message = message;
    }

    public static ExceptionCode from(String message) {
        return Arrays.stream(values())
                .filter(exceptionCode -> exceptionCode.message.equals(message))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ExceptionCode입니다."));
    }
}
