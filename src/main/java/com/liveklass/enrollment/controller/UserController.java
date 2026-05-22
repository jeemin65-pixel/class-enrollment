package com.liveklass.enrollment.controller;

import com.liveklass.enrollment.dto.request.UserCreateRequest;
import com.liveklass.enrollment.global.response.ApiResult;
import com.liveklass.enrollment.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ApiResult<Long> createUser(@RequestBody UserCreateRequest request) {
        return ApiResult.ok(userService.createUser(request));
    }
}
