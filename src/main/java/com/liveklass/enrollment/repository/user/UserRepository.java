package com.liveklass.enrollment.repository.user;

import com.liveklass.enrollment.domain.user.User;
import com.liveklass.enrollment.global.exception.CustomException;
import com.liveklass.enrollment.global.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    default User findByIdOrThrow(Long id, ErrorCode errorCode) {
        return findById(id).orElseThrow(() -> new CustomException(errorCode));
    }

    boolean existsByEmail(String email);


}
