package com.liveklass.enrollment.domain.enrollment;

import com.liveklass.enrollment.domain.lecture.Lecture;
import com.liveklass.enrollment.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Enrollment")
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus enrollmentStatus;

    private LocalDateTime confirmedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // FK 연관 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Enrollment(Lecture lecture, User user) {
        this.lecture = lecture;
        this.user = user;
        this.enrollmentStatus = EnrollmentStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void cancel() {
        this.enrollmentStatus = EnrollmentStatus.CANCELLED;
    }

    public void confirm() {
        this.enrollmentStatus = EnrollmentStatus.CONFIRMED;
        this.confirmedAt = LocalDateTime.now();
    }

}
