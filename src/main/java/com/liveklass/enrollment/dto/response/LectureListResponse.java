package com.liveklass.enrollment.dto.response;

import com.liveklass.enrollment.domain.lecture.LectureStatus;

import java.time.LocalDate;
import java.util.List;

public record LectureListResponse(
        List<LectureInfo> lectures
) {
    public record LectureInfo(
            Long lectureId,
            String title,
            int price,
            int capacity,
            int currentEnrollment,
            LectureStatus lectureStatus,
            LocalDate startDate,
            LocalDate endDate
    ) {}
}