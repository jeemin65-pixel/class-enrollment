package com.liveklass.enrollment.dto.response;

import com.liveklass.enrollment.domain.enrollment.Enrollment;
import com.liveklass.enrollment.domain.enrollment.EnrollmentStatus;

import java.time.LocalDateTime;

public record UserEnrollmentList(
            Long enrollmentId,
            Long lectureId,
            String title,
            EnrollmentStatus status,
            LocalDateTime confirmedAt,
            LocalDateTime createdAt
    ) {
    public static UserEnrollmentList from(final Enrollment enrollment) {
        return new UserEnrollmentList(
                enrollment.getId(),
                enrollment.getLecture().getId(),
                enrollment.getLecture().getTitle(),
                enrollment.getEnrollmentStatus(),
                enrollment.getConfirmedAt(),
                enrollment.getCreatedAt()
        );
    }
}

