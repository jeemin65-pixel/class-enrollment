package com.liveklass.enrollment.service.enrollment;

import com.liveklass.enrollment.domain.lecture.Lecture;
import com.liveklass.enrollment.domain.lecture.LectureStatus;
import com.liveklass.enrollment.domain.user.Role;
import com.liveklass.enrollment.domain.user.User;
import com.liveklass.enrollment.dto.request.EnrollmentCreateRequest;
import com.liveklass.enrollment.repository.enrollment.EnrollmentRepository;
import com.liveklass.enrollment.repository.lecture.LectureRepository;
import com.liveklass.enrollment.repository.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EnrollmentConcurrencyTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    private User creator;
    private User student;
    private Lecture lecture;

    @BeforeEach
    void setUp() {
        creator = userRepository.save(User.builder()
                .name("강사")
                .email("creator@test.com")
                .role(Role.CREATOR)
                .build());

        lecture = lectureRepository.save(Lecture.builder()
                .title("테스트 강의")
                .description("테스트 설명")
                .price(50000)
                .capacity(5)
                .startDate(LocalDate.of(2026, 7, 1))
                .endDate(LocalDate.of(2026, 8, 31))
                .creator(creator)
                .build());

        lecture.changeStatus(LectureStatus.OPEN);
        lectureRepository.save(lecture);
    }

    @AfterEach
    void tearDown() {
        enrollmentRepository.deleteAll();
        lectureRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("동시에 여러 명이 수강 신청해도 정원 초과되지 않는다")
    void createEnrollment_concurrency() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            User student = userRepository.save(User.builder()
                    .name("학생" + index)
                    .email("student" + index + "@test.com")
                    .role(Role.STUDENT)
                    .build());

            executorService.submit(() -> {
                try {
                    enrollmentService.createEnrollment(student.getId(),
                            new EnrollmentCreateRequest(lecture.getId()));
                } catch (Exception e) {
                    // 정원 초과 예외는 무시
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Lecture result = lectureRepository.findById(lecture.getId()).orElseThrow();
        assertThat(result.getCurrentEnrollment()).isEqualTo(5); // 정원 5명 초과 안 됨
    }
}
