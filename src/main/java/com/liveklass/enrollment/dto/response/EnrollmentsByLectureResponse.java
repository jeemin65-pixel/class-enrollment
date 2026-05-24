package com.liveklass.enrollment.dto.response;

import com.liveklass.enrollment.domain.enrollment.Enrollment;
import com.liveklass.enrollment.domain.enrollment.EnrollmentStatus;
import com.liveklass.enrollment.domain.lecture.Lecture;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record EnrollmentsByLectureResponse(
        Long id,
        String title,
        int capacity,
        int currentEnrollment,
        List<EnrollmentInfo> enrollments
) {
    public static EnrollmentsByLectureResponse from(Lecture lecture, List<Enrollment> enrollments) {
        return new EnrollmentsByLectureResponse(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getCapacity(),
                lecture.getCurrentEnrollment(),
                enrollments.stream()
                        .map(EnrollmentInfo::from)
                        .collect(Collectors.toList())
        );
    }
    public record EnrollmentInfo(
            Long enrollmentId,
            Long userId,
            String name,
            String email,
            EnrollmentStatus status,
            LocalDateTime confirmedAt,
            LocalDateTime createdAt
    ) {
        public static EnrollmentInfo from(Enrollment enrollment) {
            return new EnrollmentInfo(
                    enrollment.getId(),
                    enrollment.getUser().getId(),
                    enrollment.getUser().getName(),
                    enrollment.getUser().getEmail(),
                    enrollment.getEnrollmentStatus(),
                    enrollment.getConfirmedAt(),
                    enrollment.getCreatedAt()
            );
        }
    }
}
