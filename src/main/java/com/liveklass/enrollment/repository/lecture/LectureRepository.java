package com.liveklass.enrollment.repository.lecture;

import com.liveklass.enrollment.domain.lecture.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
