package com.liveklass.enrollment.dto.response;

import com.liveklass.enrollment.domain.lecture.Lecture;
import com.liveklass.enrollment.domain.lecture.LectureStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record LectureListResponse(
        List<LectureInfo> lectures
) {
    public static LectureListResponse from(List<Lecture> lectures) {
        return new LectureListResponse(
                lectures.stream()
                        .map(LectureInfo::from)
                        .collect(Collectors.toList())
        );
    }
    public record LectureInfo(
            Long lectureId,
            String title,
            int price,
            int capacity,
            int currentEnrollment,
            LectureStatus lectureStatus,
            LocalDate startDate,
            LocalDate endDate
    ) {
        public static LectureInfo from(Lecture lecture){
            return new LectureInfo(
                    lecture.getId(),
                    lecture.getTitle(),
                    lecture.getPrice(),
                    lecture.getCapacity(),
                    lecture.getCurrentEnrollment(),
                    lecture.getLectureStatus(),
                    lecture.getStartDate(),
                    lecture.getEndDate()
            );
        }
    }
}