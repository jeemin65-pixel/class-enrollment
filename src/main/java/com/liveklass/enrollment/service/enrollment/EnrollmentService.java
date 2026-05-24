package com.liveklass.enrollment.service.enrollment;

import com.liveklass.enrollment.domain.enrollment.Enrollment;
import com.liveklass.enrollment.domain.enrollment.EnrollmentStatus;
import com.liveklass.enrollment.domain.lecture.Lecture;
import com.liveklass.enrollment.domain.lecture.LectureStatus;
import com.liveklass.enrollment.domain.user.Role;
import com.liveklass.enrollment.domain.user.User;
import com.liveklass.enrollment.dto.request.EnrollmentCreateRequest;
import com.liveklass.enrollment.dto.response.UserEnrollmentList;
import com.liveklass.enrollment.global.exception.CustomException;
import com.liveklass.enrollment.global.exception.ErrorCode;
import com.liveklass.enrollment.global.support.Preconditions;
import com.liveklass.enrollment.repository.enrollment.EnrollmentRepository;
import com.liveklass.enrollment.repository.lecture.LectureRepository;
import com.liveklass.enrollment.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    public void createEnrollment(Long userId, EnrollmentCreateRequest request) {
        User user = userRepository.findByIdOrThrow(userId, ErrorCode.NOT_FOUND_USER);

        Preconditions.validate(user.getRole() == Role.STUDENT, ErrorCode.NOT_STUDENT);

        Lecture lecture = lectureRepository.findByIdWithLock(request.lectureId())
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_LECTURE));

        // 강의 상태 검증
        Preconditions.validate(lecture.getLectureStatus() == LectureStatus.OPEN,
                ErrorCode.LECTURE_NOT_OPEN);

        // 강의 정원 검증
        Preconditions.validate(lecture.getCurrentEnrollment() < lecture.getCapacity(),
                ErrorCode.LECTURE_FULL);

        Enrollment enrollment = Enrollment.builder()
                .lecture(lecture)
                .user(user)
                .build();

        lecture.increaseEnrollment();

        enrollmentRepository.save(enrollment);
    }

    public void cancelEnrollment(Long enrollmentId, Long userId) {
        Enrollment enrollment = enrollmentRepository.findByIdOrThrow(enrollmentId, ErrorCode.NOT_FOUND_ENROLLMENT);
        User user = userRepository.findByIdOrThrow(userId, ErrorCode.NOT_FOUND_USER);

        // 본인 수강 신청인지 검증
        Preconditions.validate(enrollment.getUser().getId().equals(userId), ErrorCode.NOT_MY_ENROLLMENT);

        // 이미 취소된 강의인지 검증
        Preconditions.validate(enrollment.getEnrollmentStatus() != EnrollmentStatus.CANCELLED,
                ErrorCode.ALREADY_CANCELLED);

        // 취소 가능 기간 검증 (결제 후 7일 이내)
        if (enrollment.getConfirmedAt() != null) {
            Preconditions.validate(enrollment.getConfirmedAt().plusDays(7).isAfter(LocalDateTime.now()),
                    ErrorCode.CANCEL_PERIOD_EXPIRED);
        }

        enrollment.cancel();
    }

    public void confirmEnrollment(Long enrollmentId, Long userId) {
        Enrollment enrollment = enrollmentRepository.findByIdOrThrow(enrollmentId, ErrorCode.NOT_FOUND_ENROLLMENT);
        User user = userRepository.findByIdOrThrow(userId, ErrorCode.NOT_FOUND_USER);

        Preconditions.validate(enrollment.getUser().getId().equals(userId), ErrorCode.NOT_MY_ENROLLMENT);
        Preconditions.validate(enrollment.getEnrollmentStatus() == EnrollmentStatus.PENDING,
                ErrorCode.ALREADY_CONFIRMED);

        enrollment.confirm();
    }

    @Transactional(readOnly = true)
    public Page<UserEnrollmentList> getMyEnrollmentList(Long userId, Pageable pageable) {
        User user = userRepository.findByIdOrThrow(userId, ErrorCode.NOT_FOUND_USER);

        return enrollmentRepository.findAllByUser(user, pageable)
                .map(UserEnrollmentList::from);
    }
}
