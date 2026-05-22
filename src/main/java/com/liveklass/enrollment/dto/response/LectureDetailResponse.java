package com.liveklass.enrollment.dto.response;

import com.liveklass.enrollment.domain.lecture.LectureStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LectureDetailResponse(
        Long id,
        String title,
        String description,
        int price,
        int capacity,
        int currentEnrollment,
        LectureStatus status,
        LocalDate startDate,
        LocalDate endDate,
        Long creatorId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
