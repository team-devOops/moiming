package com.moiming.meet.domain;

import com.moiming.core.Flag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MeetInfoTest {
    @Test
    @DisplayName("모임 삭제를 통한 사용여부 N으로 변경")
    void meetRemove() {
        //given
        MeetInfo meetInfo = MeetInfo.builder()
                .meetId(1L)
                .name("name")
                .description("description")
                .useYn(Flag.Y)
                .build();

        //when
        meetInfo.meetRemove();

        //then
        assertThat(meetInfo.getUseYn()).isEqualTo(Flag.N);
    }
}