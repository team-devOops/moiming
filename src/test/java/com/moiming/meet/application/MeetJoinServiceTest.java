package com.moiming.meet.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import com.moiming.core.Flag;
import com.moiming.core.exception.CustomNoResultException;
import com.moiming.meet.domain.Level;
import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.domain.MeetJoinUser;
import com.moiming.meet.dto.MeetJoinRequest;
import com.moiming.meet.dto.MeetJoinResponse;
import com.moiming.meet.infra.MeetInfoRepository;
import com.moiming.meet.infra.MeetJoinUserRepository;
import jakarta.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MeetJoinServiceTest {
    @InjectMocks
    private MeetJoinService service;

    @Mock
    private MeetInfoRepository meetInfoRepository;

    @Mock
    private MeetJoinUserRepository meetJoinUserRepository;

    @Nested
    @DisplayName("가입된 모임 정보 조회")
    class findMeetJoinInfo {
        @Test
        @DisplayName("가입된 모임 정보 성공")
        void findSuccess() {
            //given
            final Long meetJoinId = 1L;

            final MeetInfo expectedMeetInfo = MeetInfo.builder()
                .meetId(1L)
                .name("name")
                .description("description")
                .createDate(LocalDate.now())
                .useYn(Flag.Y)
                .build();

            final MeetJoinUser expectedMeetJoinUser = MeetJoinUser.builder()
                .meetJoinId(meetJoinId)
                .meetInfo(expectedMeetInfo)
                .userSeq(1L)
                .nickname("nickname")
                .level(Level.MEMBER)
                .useYn(Flag.Y)
                .joinDate(LocalDate.now())
                .build();

            given(meetJoinUserRepository.findById(meetJoinId)).willReturn(Optional.ofNullable(expectedMeetJoinUser));

            //when
            MeetJoinResponse response = service.findByMeetJoinId(meetJoinId);

            //then
            assertThat(response).isEqualTo(MeetJoinResponse.fromEntity(expectedMeetJoinUser));
        }

        @Test
        @DisplayName("없는 가입정보 조회 시 실패")
        void invalidMeetJoinId() {
            //given
            final Long meetJoinId = 9999L;

            given(meetJoinUserRepository.findById(meetJoinId)).willThrow(CustomNoResultException.class);

            //when & then
            assertThrows(CustomNoResultException.class, () -> service.findByMeetJoinId(meetJoinId));
        }
    }

    @Nested
    @DisplayName("모임 생성")
    class meetJoin {
        @Test
        @DisplayName("모임 가입 성공")
        void joinSuccess() {
            //given
            final Long meetJoinId = 1L;

            final Long meetId = 1L;
            final Long userSeq = 999L;
            final String nickname = "싱그러운 파인애플";
            final Level level = Level.ADMIN;
            final Flag useYn = Flag.Y;
            final LocalDate joinDate = LocalDate.now();

            final MeetJoinRequest request = MeetJoinRequest.builder()
                    .meetId(meetId)
                    .userSeq(userSeq)
                    .nickname(nickname)
                    .level(level)
                    .build();

            final MeetInfo expectedMeetInfo = MeetInfo.builder()
                    .meetId(meetId)
                    .name("name")
                    .description("description")
                    .createDate(LocalDate.now())
                    .useYn(Flag.Y)
                    .build();

            final MeetJoinUser expectedMeetJoinUser = MeetJoinUser.builder()
                    .meetJoinId(meetJoinId)
                    .meetInfo(expectedMeetInfo)
                    .userSeq(userSeq)
                    .nickname(nickname)
                    .level(level)
                    .useYn(useYn)
                    .joinDate(joinDate)
                    .build();

            given(meetInfoRepository.findById(meetId)).willReturn(Optional.ofNullable(expectedMeetInfo));
            given(meetJoinUserRepository.save(any(MeetJoinUser.class))).willReturn(expectedMeetJoinUser);

            //when
            Long response = service.register(request);

            //then
            assertThat(meetJoinId).isEqualTo(response);
        }

        @Test
        @DisplayName("없는 모임 가입시 실패")
        void invalidMeetIdFailed() {
            //given
            final Long meetId = 999L;

            final MeetJoinRequest request = MeetJoinRequest.builder()
                    .meetId(meetId)
                    .build();

            given(meetInfoRepository.findById(meetId)).willThrow(NoResultException.class);

            //when & then
            assertThrows(NoResultException.class, () -> service.register(request));
        }
    }
}
