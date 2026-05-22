package com.liveklass.enrollment.dto.response;

import com.liveklass.enrollment.domain.enrollment.EnrollmentStatus;

import java.time.LocalDateTime;

public record UserEnrollmentList(
            Long enrollmentId,
            Long lectureId,
            String title,
            EnrollmentStatus status,
            LocalDateTime confirmedAt,
            LocalDateTime createdAt
    ) {}

