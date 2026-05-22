package com.liveklass.enrollment.global.support;

import com.liveklass.enrollment.global.exception.CustomException;
import com.liveklass.enrollment.global.exception.ErrorCode;

public class Preconditions {
    public static void validate(boolean expression, ErrorCode errorCode) {
        if (!expression) {
            throw new CustomException(errorCode);
        }
    }
}
