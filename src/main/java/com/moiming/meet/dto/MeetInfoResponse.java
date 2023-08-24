package com.moiming.meet.dto;

import com.moiming.meet.domain.MeetInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "모임 단건 조회 응답 DTO")
public class MeetInfoResponse {
    @Schema(description = "모임 ID", example = "1")
    private Long meetId;

    @Schema(description = "모임명", example = "moiming 관리자 모임")
    private String name;

    @Schema(description = "모임 설명", example = "moiming 개발자 모임을 위한 모임입니다.")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "모임 생성일", example = "2023-08-23")
    private LocalDate createDate;

    public static MeetInfoResponse fromEntity(MeetInfo meetInfo) {
        return MeetInfoResponse.builder()
                .meetId(meetInfo.getMeetId())
                .name(meetInfo.getName())
                .description(meetInfo.getDescription())
                .createDate(meetInfo.getCreateDate())
                .build();
    }
}
