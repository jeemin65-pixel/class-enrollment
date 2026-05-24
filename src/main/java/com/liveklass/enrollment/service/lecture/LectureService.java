package com.liveklass.enrollment.service.lecture;

import com.liveklass.enrollment.domain.enrollment.Enrollment;
import com.liveklass.enrollment.domain.lecture.Lecture;
import com.liveklass.enrollment.domain.lecture.LectureStatus;
import com.liveklass.enrollment.domain.user.Role;
import com.liveklass.enrollment.domain.user.User;
import com.liveklass.enrollment.dto.request.LectureCreateRequest;
import com.liveklass.enrollment.dto.request.LectureUpdateRequest;
import com.liveklass.enrollment.dto.response.EnrollmentsByLectureResponse;
import com.liveklass.enrollment.dto.response.LectureDetailResponse;
import com.liveklass.enrollment.dto.response.LectureListResponse;
import com.liveklass.enrollment.global.exception.CustomException;
import com.liveklass.enrollment.global.exception.ErrorCode;
import com.liveklass.enrollment.global.support.Preconditions;
import com.liveklass.enrollment.repository.enrollment.EnrollmentRepository;
import com.liveklass.enrollment.repository.lecture.LectureRepository;
import com.liveklass.enrollment.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureService {
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    public void createLecture(Long creatorId, LectureCreateRequest request) {
        User creator = userRepository.findByIdOrThrow(creatorId, ErrorCode.NOT_FOUND_USER);

        if(creator.getRole() != Role.CREATOR) {
            throw new CustomException(ErrorCode.NOT_CREATOR);
        }

        Lecture lecture = Lecture.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .capacity(request.capacity())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .creator(creator)
                .build();

        lectureRepository.save(lecture);
    }

    public void updateLectureInfo(Long lectureId, Long creatorId, LectureUpdateRequest request) {
        Lecture lecture = lectureRepository.findByIdOrThrow(lectureId, ErrorCode.NOT_FOUND_LECTURE);
        User creator = userRepository.findByIdOrThrow(creatorId, ErrorCode.NOT_FOUND_USER);

        if(creator.getRole() != Role.CREATOR) {
            throw new CustomException(ErrorCode.NOT_CREATOR);
        }

        lecture.update(
                request.title(),
                request.description(),
                request.price(),
                request.capacity(),
                request.startDate(),
                request.endDate(),
                request.lectureStatus()
        );
    }

    public void deleteLecture(Long lectureId, Long creatorId) {
        Lecture lecture = lectureRepository.findByIdOrThrow(lectureId, ErrorCode.NOT_FOUND_LECTURE);
        User creator = userRepository.findByIdOrThrow(creatorId, ErrorCode.NOT_FOUND_USER);

        if(creator.getRole() != Role.CREATOR) {
            throw new CustomException(ErrorCode.NOT_CREATOR);
        }

        lectureRepository.deleteById(lectureId);
    }

    @Transactional(readOnly = true)
    public EnrollmentsByLectureResponse getEnrollmentsByLecture(Long lectureId, Long creatorId) {
        Lecture lecture = lectureRepository.findByIdOrThrow(lectureId, ErrorCode.NOT_FOUND_LECTURE);
        User creator = userRepository.findByIdOrThrow(creatorId, ErrorCode.NOT_FOUND_USER);

        if(creator.getRole() != Role.CREATOR) {
            throw new CustomException(ErrorCode.NOT_CREATOR);
        }

        // 해당 강의의 크리에이터가 맞는지 검증
        Preconditions.validate(lecture.getCreator().getId().equals(creatorId), ErrorCode.NOT_CREATOR);

        List<Enrollment> enrollments = enrollmentRepository.findAllBylecture(lecture);

        List<EnrollmentsByLectureResponse.EnrollmentInfo> enrollmentInfos = enrollments.stream()
                .map(enrollment -> new EnrollmentsByLectureResponse.EnrollmentInfo(
                        enrollment.getId(),
                        enrollment.getUser().getId(),
                        enrollment.getUser().getName(),
                        enrollment.getUser().getEmail(),
                        enrollment.getEnrollmentStatus(),
                        enrollment.getConfirmedAt(),
                        enrollment.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new EnrollmentsByLectureResponse(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getCapacity(),
                lecture.getCurrentEnrollment(),
                enrollmentInfos
        );
    }

    public LectureListResponse getLectureListByStatus(LectureStatus status) {
        List<Lecture> lectures = status == null
                ? lectureRepository.findAll() // 전체 조회
                : lectureRepository.findAllByLectureStatus(status); // 상태 별 필터링

        return LectureListResponse.from(lectures);
    }

    public LectureDetailResponse getLectureDetail(Long lectureId) {
        Lecture lecture = lectureRepository.findByIdOrThrow(lectureId, ErrorCode.NOT_FOUND_LECTURE);

        return LectureDetailResponse.from(lecture);
    }
}