package com.moiming.user.dto;


import java.time.LocalDate;
import lombok.Builder;

@Builder
public record SignUpRequest(
        String userId,
        String password,
        String email,
        String name,
        LocalDate birthDate
) {
}
