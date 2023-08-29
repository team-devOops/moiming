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
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.moiming.meet.dto.MeetValidateMessage.*;

@Getter
@Builder
@Schema(description = "모임 가입 정보 응답 DTO")
public class MeetJoinResponse {
    @Schema(description = "모임 가입 ID", example = "1")
    private Long meetJoinId;

    @Schema(description = "회원 ID", example = "1")
    private Long userSeq;

    @Schema(description = "모임 정보")
    private MeetInfo meetInfo;

    @Schema(description = "닉네임", example = "얄미운 복숭아")
    private String nickname;

    @Schema(description = "등급", example = "MEMBER")
    private Level level;

    @Schema(description = "가입 여부", example = "Y")
    private Flag useYn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "모임 가입일", example = "2023-03-02")
    private LocalDate joinDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MeetJoinResponse that = (MeetJoinResponse) o;
        return Objects.equals(meetJoinId, that.meetJoinId) && Objects.equals(userSeq, that.userSeq)
            && Objects.equals(meetInfo, that.meetInfo) && Objects.equals(nickname, that.nickname)
            && level == that.level && useYn == that.useYn && Objects.equals(joinDate, that.joinDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetJoinId, userSeq, meetInfo, nickname, level, useYn, joinDate);
    }

    public static MeetJoinResponse fromEntity(MeetJoinUser meetJoinUser) {
        return MeetJoinResponse.builder()
                .meetJoinId(meetJoinUser.getMeetJoinId())
                .userSeq(meetJoinUser.getUserSeq())
                .meetInfo(meetJoinUser.getMeetInfo())
                .nickname(meetJoinUser.getNickname())
                .level(meetJoinUser.getLevel())
                .useYn(meetJoinUser.getUseYn())
                .joinDate(meetJoinUser.getJoinDate())
                .build();
    }
}
