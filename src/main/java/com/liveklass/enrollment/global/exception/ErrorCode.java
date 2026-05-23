package com.liveklass.enrollment.global.exception;


import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // user
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다.")
    ;

    private final HttpStatus status;
    private final String message;
}