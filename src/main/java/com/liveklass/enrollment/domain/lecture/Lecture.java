package com.liveklass.enrollment.domain.lecture;

import com.liveklass.enrollment.domain.enrollment.Enrollment;
import com.liveklass.enrollment.domain.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Lecture")
@NoArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private int currentEnrollment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LectureStatus lectureStatus;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "lecture")
    private List<Enrollment> enrollments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @Builder
    private Lecture(String title, String description, int capacity, int price, LocalDate startDate,
                    LocalDate endDate, User creator) {
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lectureStatus = LectureStatus.DRAFT;
        this.creator = creator;
        this.currentEnrollment = 0;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String description, Integer price, Integer capacity,
                                  LocalDate startDate, LocalDate endDate, LectureStatus lectureStatus) {
        if (title != null) this.title = title;
        if (description != null) this.description = description;
        if (price != null) this.price = price;
        if (capacity != null) this.capacity = capacity;
        if (startDate != null) this.startDate = startDate;
        if (endDate != null) this.endDate = endDate;
        if (lectureStatus != null) this.lectureStatus = lectureStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void increaseEnrollment() {
        this.currentEnrollment++;
    }

    public void changeStatus(LectureStatus lectureStatus) {
        this.lectureStatus = lectureStatus;
    }



}
