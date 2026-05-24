package com.liveklass.enrollment.dto.response;

import com.liveklass.enrollment.domain.lecture.Lecture;
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
    public static LectureDetailResponse from(final Lecture lecture) {
        return new LectureDetailResponse(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getDescription(),
                lecture.getPrice(),
                lecture.getCapacity(),
                lecture.getCurrentEnrollment(),
                lecture.getLectureStatus(),
                lecture.getStartDate(),
                lecture.getEndDate(),
                lecture.getCreator().getId(),
                lecture.getCreatedAt(),
                lecture.getUpdatedAt()
        );
    }
}
