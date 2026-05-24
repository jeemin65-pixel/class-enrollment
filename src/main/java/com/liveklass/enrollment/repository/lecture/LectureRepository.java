package com.liveklass.enrollment.repository.lecture;

import com.liveklass.enrollment.domain.lecture.Lecture;
import com.liveklass.enrollment.domain.lecture.LectureStatus;
import com.liveklass.enrollment.global.exception.CustomException;
import com.liveklass.enrollment.global.exception.ErrorCode;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    default Lecture findByIdOrThrow(Long id, ErrorCode errorCode) {
        return findById(id).orElseThrow(() -> new CustomException(errorCode));
    }

    List<Lecture> findAllByLectureStatus(LectureStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Lecture l WHERE l.id = :id")
    Optional<Lecture> findByIdWithLock(@Param("id") Long id);
}
