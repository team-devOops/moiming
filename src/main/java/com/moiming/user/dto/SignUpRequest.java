package com.moiming.user.dto;


import java.time.LocalDate;
import lombok.Builder;

@Builder
public record SignUpRequest(
        String id,
        String password,
        String email,
        String name,
        LocalDate birthDate
) {
}
