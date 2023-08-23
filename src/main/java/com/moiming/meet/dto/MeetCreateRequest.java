package com.moiming.meet.dto;

import com.moiming.meet.domain.MeetInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

import static com.moiming.meet.dto.MeetValidateMessage.NAME_IS_NOT_NULL;
import static com.moiming.meet.dto.MeetValidateMessage.NAME_SIZE_INVALID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "모임 생성 요청 DTO")
public class MeetCreateRequest {
    @Size(max = 64, message = NAME_SIZE_INVALID)
    @NotEmpty(message = NAME_IS_NOT_NULL)
    @Schema(description = "모임명", example = "moiming 관리자 모임")
    private String name;

    @Schema(description = "모임 설명", example = "moiming 개발자 모임을 위한 모임입니다.")
    private String description;

    public MeetInfo toEntity() {
        return MeetInfo.builder()
                .name(this.name)
                .description(this.description)
                .createDate(LocalDate.now())
                .build();
    }
}
