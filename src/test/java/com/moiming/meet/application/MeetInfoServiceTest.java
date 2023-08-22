package com.moiming.meet.application;

import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.infra.MeetInfoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

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
