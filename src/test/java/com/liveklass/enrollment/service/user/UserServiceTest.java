package com.liveklass.enrollment.service.user;

import com.liveklass.enrollment.domain.user.Role;
import com.liveklass.enrollment.domain.user.User;
import com.liveklass.enrollment.dto.request.UserCreateRequest;
import com.liveklass.enrollment.global.exception.CustomException;
import com.liveklass.enrollment.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 생성 성공")
    void createUser_success() {
        // given
        UserCreateRequest request = new UserCreateRequest("홍길동", "hong@email.com", Role.STUDENT);
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .role(request.role())
                .build();

        given(userRepository.existsByEmail(request.email())).willReturn(false);
        given(userRepository.save(any())).willReturn(user);

        // when
        Long result = userService.createUser(request);

        // then
        then(userRepository).should().save(any());
    }

    @Test
    @DisplayName("이메일 중복 시 예외 발생")
    void createUser_duplicateEmail_throwsException() {
        // given
        UserCreateRequest request = new UserCreateRequest("홍길동", "hong@email.com", Role.STUDENT);
        given(userRepository.existsByEmail(request.email())).willReturn(true);

        // when & then
        assertThatThrownBy(() -> userService.createUser(request))
                .isInstanceOf(CustomException.class)
                .hasMessage("중복된 이메일입니다.");
    }
}
