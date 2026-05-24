package com.liveklass.enrollment.service.lecture;

import com.liveklass.enrollment.domain.user.Role;
import com.liveklass.enrollment.domain.user.User;
import com.liveklass.enrollment.dto.request.LectureCreateRequest;
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

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class LectureServiceTest {

    @InjectMocks
    private LectureService lectureService;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    private User creator;
    private User student;
    private LectureCreateRequest lectureCreateRequest;

    @BeforeEach
    void setUp() {
        creator = User.builder()
                .name("강사")
                .email("creator@test.com")
                .role(Role.CREATOR)
                .build();

        student = User.builder()
                .name("학생")
                .email("student@test.com")
                .role(Role.STUDENT)
                .build();

        lectureCreateRequest = new LectureCreateRequest(
                "테스트 강의",
                "테스트 설명",
                50000,
                10,
                LocalDate.of(2026, 7, 1),
                LocalDate.of(2026, 8, 31)
        );
    }

    @Test
    @DisplayName("강의 등록 성공")
    void createLecture_success() {
        // given
        given(userRepository.findByIdOrThrow(any(), any())).willReturn(creator);

        // when & then
        assertThatNoException().isThrownBy(() -> lectureService.createLecture(1L, lectureCreateRequest));
        then(lectureRepository).should().save(any());
    }

    @Test
    @DisplayName("CREATOR가 아닌 유저가 강의 등록 시 예외 발생")
    void createLecture_notCreator_throwsException() {
        // given
        given(userRepository.findByIdOrThrow(any(), any())).willReturn(student);

        // when & then
        assertThatThrownBy(() -> lectureService.createLecture(1L, lectureCreateRequest))
                .isInstanceOf(CustomException.class);
    }
}