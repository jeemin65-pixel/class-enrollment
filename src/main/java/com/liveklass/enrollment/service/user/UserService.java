package com.liveklass.enrollment.service.user;

import com.liveklass.enrollment.domain.user.User;
import com.liveklass.enrollment.dto.request.UserCreateRequest;
import com.liveklass.enrollment.global.exception.CustomException;
import com.liveklass.enrollment.global.exception.ErrorCode;
import com.liveklass.enrollment.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Long createUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .role(request.role())
                .build();

       return userRepository.save(user).getId();
    }
}
