package com.moiming.meet.dto;

import com.moiming.core.Flag;
import com.moiming.meet.domain.Level;
import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.domain.MeetJoinUser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.moiming.meet.dto.MeetValidateMessage.*;

@Getter
@Builder
@Schema(description = "모임 가입 요청 DTO")
public class MeetJoinRequest {
    @Min(value = 1, message = MEET_ID_IS_NOT_NULL)
    @NotNull(message = MEET_ID_IS_NOT_NULL)
    @Schema(description = "모임 ID", example = "133")
    private Long meetId;

    //TODO: 회원 구현 되면 수정 필요
    @NotNull(message = MEET_ID_IS_NOT_NULL)
    @Schema(description = "회원 SEQ", example = "337")
    private Long userSeq;

    @Size(min = 1, max = 16, message = NICKNAME_SIZE_INVALID)
    @NotEmpty(message = NICKNAME_IS_NOT_NULL)
    @Schema(description = "닉네임", example = "부끄러운 복숭아")
    private String nickname;

    @NotNull(message = LEVEL_IS_NOT_EMPTY)
    @Schema(description = "등급", example = "MEMBER")
    private Level level;

    public MeetJoinUser toEntity(MeetInfo meetInfo) {
        return MeetJoinUser.builder()
                .meetInfo(meetInfo)
                .userSeq(userSeq)
                .nickname(nickname)
                .level(level)
                .useYn(Flag.Y)
                .build();
    }
}
