package com.liveklass.enrollment.dto.request;

import com.liveklass.enrollment.domain.user.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotBlank(message = "이름은 필수 입력 사항입니다")
        String name,
        @NotBlank(message = "이메일은 필수 입력 사항입니다")
        String email,
        @NotNull(message = "역할은 필수 입력 사항입니다")
        Role role
) {
}
