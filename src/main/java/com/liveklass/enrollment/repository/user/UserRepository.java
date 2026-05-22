package com.liveklass.enrollment.repository.user;

import com.liveklass.enrollment.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
