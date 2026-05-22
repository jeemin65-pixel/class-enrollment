package com.liveklass.enrollment.controller;

import com.liveklass.enrollment.dto.request.EnrollmentCreateRequest;
import com.liveklass.enrollment.dto.response.UserEnrollmentList;
import com.liveklass.enrollment.global.response.ApiResult;
import com.liveklass.enrollment.service.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ApiResult<Void> createEnrollment(@RequestParam Long userId, @RequestBody EnrollmentCreateRequest request) {
        enrollmentService.createEnrollment(userId, request);
        return ApiResult.ok();
    }

    @PatchMapping("/{enrollmentId}/cancel")
    public ApiResult<Void> cancelEnrollment(@PathVariable Long enrollmentId, @RequestParam Long userId) {
        enrollmentService.cancelEnrollment(enrollmentId, userId);
        return ApiResult.ok();
    }

    @PatchMapping("/{enrollmentId}/confirm")
    public ApiResult<Void> confirmEnrollment(@PathVariable Long enrollmentId, @RequestParam Long userId) {
        enrollmentService.confirmEnrollment(enrollmentId, userId);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult<Page<UserEnrollmentList>> getMyEnrollmentList(@RequestParam Long userId, Pageable pageable) {
        return ApiResult.ok(enrollmentService.getMyEnrollmentList(userId, pageable));
    }
}
