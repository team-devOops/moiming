package com.moiming.meet.application;

import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.infra.MeetInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MeetInfoServiceTest {
    @InjectMocks
    private MeetInfoService service;

    @Mock
    private MeetInfoRepository meetInfoRepository;

    @Nested
    @DisplayName("모임 생성")
    class meetRegister {
        @Test
        @DisplayName("모임 생성 성공")
        void registerSuccess() {
            //given
            MeetInfo meetInfo = MeetInfo.builder()
                    .name("name")
                    .description("description")
                    .build();

            given(meetInfoRepository.save(eq(meetInfo))).willReturn(meetInfo);

            //when
            MeetInfo expectedResponse = meetInfoRepository.save(meetInfo);

            //then
            assertThat(meetInfo).isEqualTo(expectedResponse);
        }
    }
}
