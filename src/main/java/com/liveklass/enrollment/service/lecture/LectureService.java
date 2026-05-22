package com.liveklass.enrollment.service.lecture;

import com.liveklass.enrollment.domain.lecture.LectureStatus;
import com.liveklass.enrollment.dto.request.LectureCreateRequest;
import com.liveklass.enrollment.dto.request.LectureUpdateRequest;
import com.liveklass.enrollment.dto.response.EnrollmentsByLectureResponse;
import com.liveklass.enrollment.dto.response.LectureDetailResponse;
import com.liveklass.enrollment.dto.response.LectureListResponse;
import com.liveklass.enrollment.repository.lecture.LectureRepository;
import com.liveklass.enrollment.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;

    //TODO : 서비스 기능 개발

    public void createLecture(Long creatorId, LectureCreateRequest request) {

    }

    public void updateLectureInfo(Long lectureId, Long creatorId, LectureUpdateRequest request) {

    }

    public void deleteLecture(Long lectureId, Long creatorId) {

    }

    public EnrollmentsByLectureResponse getEnrollmentsByLecture(Long lectureId, Long creatorId) {

        return null;
    }

    public LectureListResponse getLectureListByStatus(LectureStatus status) {

        return null;
    }

    public LectureDetailResponse getLectureDetail(Long lectureId) {

        return null;
    }
}