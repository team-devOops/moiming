package com.moiming.user.web;

import com.moiming.user.application.UserService;
import com.moiming.user.dto.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저", description = "유저 관련 API")
public class UserController {

    private final UserService userService;
    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "회원가입을 진행합니다.")
    public void signUp(@Valid @RequestBody SignUpRequest request) {
        userService.signUp(request);
    }
}
