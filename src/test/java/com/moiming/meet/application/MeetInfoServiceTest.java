package com.moiming.meet.application;

import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.dto.MeetInfoResponse;
import com.moiming.meet.infra.MeetInfoRepository;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MeetInfoServiceTest {
    @InjectMocks
    private MeetInfoService service;

    @Mock
    private MeetInfoRepository meetInfoRepository;

    @Nested
    @DisplayName("모임 단건 조회")
    class findMeet {
        @Test
        @DisplayName("모임 단건 조회 성공")
        void findSuccess() {
            //given
            MeetInfo meetInfo = MeetInfo.builder()
                    .name("name")
                    .description("description")
                    .build();

            given(meetInfoRepository.save(eq(meetInfo))).willReturn(meetInfo);
            given(meetInfoRepository.findById(eq(meetInfo.getMeetSeq()))).willReturn(Optional.of(meetInfo));

            Long meetId = service.register(meetInfo);

            //when
            MeetInfoResponse expectedResponse = service.findMeet(meetId);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(meetInfo.getMeetSeq()).isEqualTo(expectedResponse.getMeetId());
                softAssertions.assertThat(meetInfo.getName()).isEqualTo(expectedResponse.getName());
                softAssertions.assertThat(meetInfo.getDescription()).isEqualTo(expectedResponse.getDescription());
            });
        }

        @Test
        @DisplayName("없는 모임 단건 조회시 실패")
        void findFailed() {
            //given
            Long invalidMeetId = 99999L;

            //when & then
            assertThrows(NoResultException.class, () -> service.findMeet(invalidMeetId));
        }
    }

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

            MeetInfo expectedMeetInfo = MeetInfo.builder()
                    .meetSeq(1L)
                    .name(meetInfo.getName())
                    .description(meetInfo.getDescription())
                    .createDate(LocalDate.now())
                    .build();

            given(meetInfoRepository.save(eq(meetInfo))).willReturn(expectedMeetInfo);

            //when
            Long response = service.register(meetInfo);

            //then
            assertThat(response).isNotZero();
        }
    }
}
