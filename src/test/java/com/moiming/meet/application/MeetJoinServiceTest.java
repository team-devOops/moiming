package com.moiming.meet.application;

import com.moiming.meet.domain.Level;
import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.dto.MeetJoinRequest;
import com.moiming.meet.infra.MeetInfoRepository;
import com.moiming.meet.infra.MeetJoinUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class MeetJoinServiceTest {
    @InjectMocks
    private MeetJoinService service;

    @Mock
    private MeetInfoRepository meetInfoRepository;

    @Mock
    private MeetJoinUserRepository meetJoinUserRepository;

    @Nested
    @DisplayName("모임 생성")
    class meetJoin {
        @Test
        @DisplayName("모임 가입 성공")
        void joinSuccess() {
            //given
            final Long meetId = 1L;

            final MeetJoinRequest request = MeetJoinRequest.builder()
                    .meetId(1L)
                    .userSeq(999L)
                    .nickname("싱그러운 파인애플")
                    .level(Level.ADMIN)
                    .build();

//            final MeetInfo expectedMeetInfo = MeetInfo.builder()
//                    .m
//                    .build();

            given(meetInfoRepository.findById(meetId))

            //when

            //then
        }

        @Test
        @DisplayName("없는 모임 가입시 실패")
        void invalidMeetIdFailed() {

        }
    }
}