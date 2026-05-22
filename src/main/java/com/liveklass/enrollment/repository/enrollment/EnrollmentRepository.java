package com.liveklass.enrollment.repository.enrollment;

import com.liveklass.enrollment.domain.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
