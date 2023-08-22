package com.moiming.meet.infra;

import com.moiming.meet.domain.MeetInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class MeetInfoRepositoryTest {
    @Autowired
    private MeetInfoRepository repository;

    @Test
    @DisplayName("모임 저장 성공")
    void save() {
        //given
        MeetInfo meetInfo = MeetInfo.builder()
                .name("name")
                .description("description")
                .build();

        //when
        MeetInfo result = repository.save(meetInfo);

        //then
        assertThat(meetInfo).isEqualTo(result);
    }
}