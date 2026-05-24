package com.liveklass.enrollment.repository.enrollment;

import com.liveklass.enrollment.domain.enrollment.Enrollment;
import com.liveklass.enrollment.domain.lecture.Lecture;
import com.liveklass.enrollment.domain.user.User;
import com.liveklass.enrollment.global.exception.CustomException;
import com.liveklass.enrollment.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    default Enrollment findByIdOrThrow(Long id, ErrorCode errorCode) {
        return findById(id).orElseThrow(() -> new CustomException(errorCode));
    }

    List<Enrollment> findAllBylecture(Lecture lecture);

    Page<Enrollment> findAllByUser(User user, Pageable pageable);

}
