package com.moiming.meet.domain;

import com.moiming.core.Flag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MeetJoinUserTest {
    @Test
    @DisplayName("모임 가입 여부 N으로 상태 변경")
    void leave() {
        //given
        MeetJoinUser meetJoinUser = MeetJoinUser.builder()
                .meetJoinId(1L)
                .useYn(Flag.Y)
                .build();

        //when
        meetJoinUser.leave();

        //then
        assertThat(meetJoinUser.getUseYn()).isEqualTo(Flag.N);
    }
}