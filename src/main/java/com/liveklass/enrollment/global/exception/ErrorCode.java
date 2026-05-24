package com.liveklass.enrollment.global.exception;


import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // user
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다."),
    NOT_CREATOR(HttpStatus.BAD_REQUEST, "크리에이터만 강의를 등록할 수 있습니다."),

    // lecture
    NOT_FOUND_LECTURE(HttpStatus.BAD_REQUEST, "존재하지 않는 강의입니다.")
    ;

    private final HttpStatus status;
    private final String message;
}