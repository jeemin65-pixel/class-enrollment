package com.liveklass.enrollment.dto.response;

import com.liveklass.enrollment.domain.enrollment.EnrollmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public record EnrollmentsByLectureResponse(
        Long id,
        String title,
        int capacity,
        int currentEnrollment,
        List<EnrollmentInfo> enrollments
) {
    public record EnrollmentInfo(
            Long enrollmendId,
            Long userId,
            String name,
            String email,
            EnrollmentStatus status,
            LocalDateTime confirmedAt,
            LocalDateTime createdAt
    ) {}
}
