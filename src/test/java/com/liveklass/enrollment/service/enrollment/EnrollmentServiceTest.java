package com.liveklass.enrollment.service.enrollment;

import com.liveklass.enrollment.domain.lecture.Lecture;
import com.liveklass.enrollment.domain.lecture.LectureStatus;
import com.liveklass.enrollment.domain.user.Role;
import com.liveklass.enrollment.domain.user.User;
import com.liveklass.enrollment.dto.request.EnrollmentCreateRequest;
import com.liveklass.enrollment.global.exception.CustomException;
import com.liveklass.enrollment.repository.enrollment.EnrollmentRepository;
import com.liveklass.enrollment.repository.lecture.LectureRepository;
import com.liveklass.enrollment.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class EnrollmentServiceTest {
    @InjectMocks
    private EnrollmentService enrollmentService;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private UserRepository userRepository;

    private User student;
    private Lecture lecture;

    @BeforeEach
    void setUp() {
        student = User.builder()
                .name("박유저")
                .email("student@test.com")
                .role(Role.STUDENT)
                .build();

        lecture = Lecture.builder()
                .title("테스트 강의")
                .description("테스트 설명")
                .price(50000)
                .capacity(10)
                .startDate(LocalDate.of(2026, 7, 1))
                .endDate(LocalDate.of(2026, 8, 31))
                .creator(student)
                .build();

        lecture.changeStatus(LectureStatus.OPEN);
    }

    @Test
    @DisplayName("수강 신청 성공")
    void createEnrollment_success() {
        // given
        EnrollmentCreateRequest request = new EnrollmentCreateRequest(1L);
        given(userRepository.findByIdOrThrow(any(), any())).willReturn(student);
        given(lectureRepository.findByIdWithLock(any())).willReturn(Optional.of(lecture));

        // when & then
        assertThatNoException().isThrownBy(() -> enrollmentService.createEnrollment(1L, request));
        then(enrollmentRepository).should().save(any());
    }

    @Test
    @DisplayName("정원 초과 시 예외 발생")
    void createEnrollment_lectureFullException() {
        // given
        EnrollmentCreateRequest request = new EnrollmentCreateRequest(1L);

        Lecture fullLecture = Lecture.builder()
                .title("정원 찬 강의")
                .description("테스트 설명")
                .price(50000)
                .capacity(1)
                .startDate(LocalDate.of(2026, 7, 1))
                .endDate(LocalDate.of(2026, 8, 31))
                .creator(student)
                .build();

        fullLecture.changeStatus(LectureStatus.OPEN);
        fullLecture.increaseEnrollment();

        given(userRepository.findByIdOrThrow(any(), any())).willReturn(student);
        given(lectureRepository.findByIdWithLock(any())).willReturn(Optional.of(fullLecture));

        // when & then
        assertThatThrownBy(() -> enrollmentService.createEnrollment(1L, request))
                .isInstanceOf(CustomException.class);
    }
}
