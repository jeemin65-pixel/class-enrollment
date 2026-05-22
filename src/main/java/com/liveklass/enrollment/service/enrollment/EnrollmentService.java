package com.liveklass.enrollment.service.enrollment;

import com.liveklass.enrollment.dto.request.EnrollmentCreateRequest;
import com.liveklass.enrollment.dto.response.UserEnrollmentList;
import com.liveklass.enrollment.repository.enrollment.EnrollmentRepository;
import com.liveklass.enrollment.repository.lecture.LectureRepository;
import com.liveklass.enrollment.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    //TODO : 서비스 기능 개발

    public void createEnrollment(Long userId, EnrollmentCreateRequest request) {

    }

    public void cancelEnrollment(Long enrollmentId, Long userId) {

    }

    public void confirmEnrollment(Long enrollmentId, Long userId) {

    }

    public Page<UserEnrollmentList> getMyEnrollmentList(Long userId, Pageable pageable) {
        return null;
    }
}
