package com.liveklass.enrollment.controller;

import com.liveklass.enrollment.domain.lecture.LectureStatus;
import com.liveklass.enrollment.dto.response.EnrollmentsByLectureResponse;
import com.liveklass.enrollment.dto.request.LectureCreateRequest;
import com.liveklass.enrollment.dto.response.LectureDetailResponse;
import com.liveklass.enrollment.dto.response.LectureListResponse;
import com.liveklass.enrollment.dto.request.LectureUpdateRequest;
import com.liveklass.enrollment.global.response.ApiResult;
import com.liveklass.enrollment.service.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {
    private final LectureService lectureService;

    @PostMapping
    public ApiResult<Void> createLecture(@RequestParam Long creatorId,
                                         @RequestBody LectureCreateRequest request) {
        lectureService.createLecture(creatorId, request);
        return ApiResult.ok();
    }

    @PatchMapping("/{lectureId}")
    public ApiResult<Void> updateLectureInfo(@PathVariable Long lectureId, @RequestParam Long creatorId,
                                             @RequestBody LectureUpdateRequest request) {
        lectureService.updateLectureInfo(lectureId, creatorId, request);
        return ApiResult.ok();
    }

    @DeleteMapping("/{lectureId}")
    public ApiResult<Void> deleteLecture(@PathVariable Long lectureId, @RequestParam Long creatorId) {
        lectureService.deleteLecture(lectureId, creatorId);
        return ApiResult.ok();
    }

    @GetMapping("/{lectureId}/enrollments")
    public ApiResult<EnrollmentsByLectureResponse> getEnrollmentsByLecture(@PathVariable Long lectureId,
                                                                           @RequestParam Long creatorId) {
        return ApiResult.ok(lectureService.getEnrollmentsByLecture(lectureId, creatorId));
    }

    @GetMapping
    public ApiResult<LectureListResponse> getLectureListByStatus(@RequestParam(required = false) LectureStatus status) {
        return ApiResult.ok(lectureService.getLectureListByStatus(status));
    }

    @GetMapping("/{lectureId}")
    public ApiResult<LectureDetailResponse> getLectureDetail(@PathVariable Long lectureId) {
        return ApiResult.ok(lectureService.getLectureDetail(lectureId));
    }
}
