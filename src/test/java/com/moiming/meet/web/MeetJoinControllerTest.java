package com.moiming.meet.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moiming.meet.domain.Level;
import com.moiming.meet.dto.MeetJoinRequest;
import com.moiming.meet.dto.MeetJoinResponse;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MeetJoinControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private ObjectMapper objectMapper;

    final String baseUrl = "/meets/join";

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Nested
    @DisplayName("모임 가입 정보 조회")
    class meetSelect {
        @ParameterizedTest
        @ValueSource(strings = {"-1", "ㄱㄴㄷ", "ABCDE"})
        @DisplayName("잘못된 형식의 파라미터 입력시 실패")
        void invalidMeetJoinId(String invalidMeetJoinId) throws Exception {
            //when & then
            mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{meetJoinId}", invalidMeetJoinId))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("없는 모임 조회 시 오류")
        void selectFailed() throws Exception {
            //given
            final Long invalidMeetJoinId = 99999999L;

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{meetJoinId}", invalidMeetJoinId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("모임 가입 정보 조회 성공")
        void selectSuccess() throws Exception {
            //given
            MeetJoinResponse meetJoinInfo = 모임_가입_성공(MeetJoinRequest.builder()
                .meetId(999999L)
                .userSeq(1L)
                .nickname("nickname")
                .level(Level.ADMIN)
                .build());

            //when
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{meetJoinId}", meetJoinInfo.getMeetJoinId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

            MeetJoinResponse response = 응답값을_객체에_매핑함(mvcResult, MeetJoinResponse.class);

            //then
            assertThat(meetJoinInfo).isEqualTo(response);
        }
    }

    @Nested
    @DisplayName("모임 가입")
    class meetJoin {
        @Test
        @DisplayName("없는 모임 가입 시도시 오류")
        void invalidMeetInfo() throws Exception {
            //given
            final MeetJoinRequest request = MeetJoinRequest.builder()
                    .meetId(99999L)
                    .userSeq(1L)
                    .nickname("nickname")
                    .level(Level.ADMIN)
                    .build();

            String param = objectMapper.writeValueAsString(request);

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .content(param)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }
        @Test
        @DisplayName("모입 가입 성공")
        void success() throws Exception {
            //given
            final MeetJoinRequest request = MeetJoinRequest.builder()
                    .meetId(999999L)
                    .userSeq(1L)
                    .nickname("nickname")
                    .level(Level.ADMIN)
                    .build();

            MeetJoinResponse response = 모임_가입_성공(request);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(request.getMeetId()).isEqualTo(response.getMeetInfo().getMeetId());
                softAssertions.assertThat(request.getUserSeq()).isEqualTo(response.getUserSeq());
                softAssertions.assertThat(request.getNickname()).isEqualTo(response.getNickname());
                softAssertions.assertThat(request.getLevel()).isEqualTo(response.getLevel());
                softAssertions.assertThat(response.getJoinDate()).isNotNull();
            });
        }
    }

    private MeetJoinResponse 모임_가입_성공(MeetJoinRequest request) throws Exception {
        String param = objectMapper.writeValueAsString(request);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                .content(param)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();

        MvcResult mvcSelectResult = mockMvc.perform(MockMvcRequestBuilders.get(
                Objects.requireNonNull(mvcResult.getResponse().getRedirectedUrl())))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        return 응답값을_객체에_매핑함(mvcSelectResult, MeetJoinResponse.class);
    }

    private <T> T 응답값을_객체에_매핑함(MvcResult mvcResult, Class<T> type) throws Exception {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), type);
    }
}
