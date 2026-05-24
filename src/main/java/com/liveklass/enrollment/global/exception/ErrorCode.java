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
    NOT_STUDENT(HttpStatus.BAD_REQUEST, "학생만 수강 신청을 할 수 있습니다."),

    // lecture
    NOT_FOUND_LECTURE(HttpStatus.BAD_REQUEST, "존재하지 않는 강의입니다."),
    LECTURE_NOT_OPEN(HttpStatus.BAD_REQUEST, "수강 신청이 불가능한 강의입니다."),
    LECTURE_FULL(HttpStatus.BAD_REQUEST, "정원이 초과되었습니다."),

    // enrollment
    NOT_MY_ENROLLMENT(HttpStatus.FORBIDDEN, "본인의 수강 신청만 취소할 수 있습니다."),
    ALREADY_CANCELLED(HttpStatus.BAD_REQUEST, "이미 취소된 수강 신청입니다."),
    ALREADY_CONFIRMED(HttpStatus.BAD_REQUEST, "이미 확정된 수강 신청입니다."),
    CANCEL_PERIOD_EXPIRED(HttpStatus.BAD_REQUEST, "취소 가능 기간이 지났습니다."),
    NOT_FOUND_ENROLLMENT(HttpStatus.NOT_FOUND, "수강 신청 내역을 찾을 수 없습니다.")

    ;

    private final HttpStatus status;
    private final String message;
}