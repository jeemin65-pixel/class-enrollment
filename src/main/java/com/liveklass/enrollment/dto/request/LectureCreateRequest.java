package com.liveklass.enrollment.dto.request;

import com.liveklass.enrollment.domain.lecture.LectureStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LectureCreateRequest(
        @NotBlank(message = "강의 이름은 필수 입력 사항입니다")
        String title,
        @NotBlank(message = "강의 설명은 필수 입력 사항입니다")
        String description,
        @NotNull(message = "강의 가격은 필수 입력 사항입니다")
        int price,
        @NotNull(message = "강의 정원은 필수 입력 사항입니다")
        int capacity,
        @NotBlank(message = "강의 시작일은 필수 입력 사항입니다")
        LocalDate startDate,
        @NotBlank(message = "강의 종료일은 필수 입력 사항입니다")
        LocalDate endDate,
        @NotNull(message = "강의 상태는 필수 입력 사항입니다")
        LectureStatus lectureStatus
        ) {
}
