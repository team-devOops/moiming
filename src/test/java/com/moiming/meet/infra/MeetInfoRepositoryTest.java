package com.moiming.meet.infra;

import com.moiming.meet.domain.MeetInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class MeetInfoRepositoryTest {

    @Autowired
    private MeetInfoRepository repository;

    @Test
    @DisplayName("모임 조회 성공")
    void find() {
        //given
        MeetInfo meetInfo = 모임_저장_성공(MeetInfo.builder()
                .name("name")
                .description("description")
                .createDate(LocalDate.now())
                .build());

        Long meetId = meetInfo.getMeetSeq();

        //when
        MeetInfo response = repository.findById(meetId).get();

        //then
        assertThat(meetInfo).isEqualTo(response);
    }

    @Test
    @DisplayName("모임 저장 성공")
    void save() {
        //given
        MeetInfo meetInfo = MeetInfo.builder()
                .name("name")
                .description("description")
                .createDate(LocalDate.now())
                .build();

        //when
        MeetInfo result = 모임_저장_성공(meetInfo);

        //then
        assertThat(meetInfo).isEqualTo(result);
    }

    private MeetInfo 모임_저장_성공(MeetInfo meetInfo) {
        return repository.save(meetInfo);
    }
}
