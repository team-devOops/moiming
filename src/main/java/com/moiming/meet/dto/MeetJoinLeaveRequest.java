package com.moiming.meet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import static com.moiming.meet.dto.MeetValidateMessage.*;

@Getter
@Builder
@Schema(description = "모임 탈퇴 요청 DTO")
public class MeetJoinLeaveRequest {
    @NotEmpty(message = MEET_ID_IS_NOT_NULL)
    @Schema(description = "모임 ID", example = "133")
    private Long meetId;

    @NotEmpty(message = USER_ID_IS_NOT_NULL)
    @Schema(description = "USER ID", example = "1")
    private Long userSeq;
}
