package com.liveklass.enrollment.repository.lecture;

import com.liveklass.enrollment.domain.lecture.Lecture;
import com.liveklass.enrollment.domain.lecture.LectureStatus;
import com.liveklass.enrollment.global.exception.CustomException;
import com.liveklass.enrollment.global.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    default Lecture findByIdOrThrow(Long id, ErrorCode errorCode) {
        return findById(id).orElseThrow(() -> new CustomException(errorCode));
    }

    List<Lecture> findAllByLectureStatus(LectureStatus status);
}
