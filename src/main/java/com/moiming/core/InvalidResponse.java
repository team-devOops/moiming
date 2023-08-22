package com.moiming.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "유효성 검사에 실패했을 경우의 RESPONSE")
public class InvalidResponse {
    @Schema(description = "오류 발생 field명", example = "mobileNo")
    String field;

    @Schema(description = "오류 발생 field명", example = "휴대전화번호을 입력해주세요.")
    String message;

    @Schema(description = "오류 발생 field 입력 값", example = "AAA")
    Object rejectValue;
}
