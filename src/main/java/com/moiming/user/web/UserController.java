package com.moiming.user.web;

import com.moiming.user.application.UserService;
import com.moiming.user.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody SignUpRequest request) {
        userService.signUp(request);
    }
}
