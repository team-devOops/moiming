package com.moiming.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Builder;

@Builder
@Schema(description = "회원 가입 요청")
public record SignUpRequest(
        @NotNull(message = "아이디는 필수입니다.")
        @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하로 입력해주세요.")
        @Schema(description = "아이디", example = "moiming")
        String userId,

        @NotNull
        @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 입력해주세요.")
        @Schema(description = "비밀번호", example = "asddad")
        String password,

        @NotNull
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Schema(description = "이메일", example = "sadas@asd.com")
        String email,

        @NotNull
        @Size(min = 1, max = 20, message = "이름은 1자 이상 20자 이하로 입력해주세요.")
        @Schema(description = "이름", example = "이름")
        String name,

        @NotNull(message = "생년월일은 필수입니다.")
        @Schema(description = "생년월일", example = "2021-08-23")
        LocalDate birthDate
) {
}
