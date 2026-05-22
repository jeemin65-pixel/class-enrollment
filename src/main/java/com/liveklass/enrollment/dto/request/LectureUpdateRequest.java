package com.liveklass.enrollment.dto.request;

import com.liveklass.enrollment.domain.lecture.LectureStatus;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record LectureUpdateRequest(
        @Nullable
        String title,
        @Nullable
        String description,
        @Nullable
        int price,
        @Nullable
        int capacity,
        @Nullable
        LocalDate startDate,
        @Nullable
        LocalDate endDate,
        @Nullable
        LectureStatus lectureStatus
) {
}
