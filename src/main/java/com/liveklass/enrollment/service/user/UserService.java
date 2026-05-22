package com.liveklass.enrollment.service.user;

import com.liveklass.enrollment.dto.request.UserCreateRequest;
import com.liveklass.enrollment.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //TODO : 서비스 기능 개발

    public Long createUser(UserCreateRequest request) {
        return 0L;
    }
}
