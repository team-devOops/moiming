package com.moiming.meet.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moiming.core.Flag;
import com.moiming.meet.domain.MeetInfo;
import com.moiming.meet.dto.MeetCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MeetsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private ObjectMapper objectMapper;

    final String baseUrl = "/meets";

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Nested
    @DisplayName("모임 단건 조회")
    class findMeet {
        @Test
        @DisplayName("모임 단건 조회 성공")
        void findMeetSuccess() throws Exception {
            //given
            MvcResult mvcResult = 모임_생성_성공();

            //when
            mockMvc.perform(MockMvcRequestBuilders.get(mvcResult.getResponse().getRedirectedUrl()))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("모임 단건 조회 실패")
        void findMeetFailed() throws Exception {
            //given
            Long meetId = 99999L;

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/{meetId}", meetId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }
    }

    @Nested
    @DisplayName("모임 저장")
    class meetCreate {
        @Test
        @DisplayName("모임 저장 성공")
        void meetCreateSuccess() throws Exception {
            //given
            final MeetCreateRequest request = MeetCreateRequest.builder()
                    .name("name")
                    .description("description")
                    .build();

            //when
            MvcResult mvcResult = 모임_생성_성공(request);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(mvcResult.getResponse().getRedirectedUrl()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            MeetInfo response = 응답값을_객체에_매핑함(result, MeetInfo.class);

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(request.getName()).isEqualTo(response.getName());
                softAssertions.assertThat(request.getDescription()).isEqualTo(response.getDescription());
            });
        }

        @ParameterizedTest
        @ValueSource(strings = "사랑과 아침이 말 이웃 이런 나는 다 때 까닭입니다. 이름을 가득 나의 라이너 하나에 이름을 밤이 묻힌 거외다. 이국 같이 너무나 봅니다.")
        @NullAndEmptySource
        @DisplayName("모임 저장시 잘못된 모임 값 입력으로 인한 BAD REQUEST 응답")
        void meetCreateBadRequest(String name) throws Exception {
            //given
            final MeetCreateRequest request = MeetCreateRequest.builder()
                    .name(name)
                    .description("description")
                    .build();

            String param = objectMapper.writeValueAsString(request);

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .content(param)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("모임 삭제")
    class meetRemove {
        @Test
        @DisplayName("모임 삭제 성공")
        void meetRemoveSuccess() throws Exception {
            //given
            MvcResult mvcResult = 모임_생성_성공();

            MvcResult mvcSelectResponse = mockMvc.perform(MockMvcRequestBuilders.get(mvcResult.getResponse().getRedirectedUrl()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            MeetInfo meetInfo = 응답값을_객체에_매핑함(mvcSelectResponse, MeetInfo.class);

            //when
            mockMvc.perform(MockMvcRequestBuilders.delete("/meets/{meetId}", meetInfo.getMeetId()))
                    .andExpect(MockMvcResultMatchers.status().isOk());

            MvcResult mvcResponse = mockMvc.perform(MockMvcRequestBuilders.get(mvcResult.getResponse().getRedirectedUrl()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

            MeetInfo response = 응답값을_객체에_매핑함(mvcResponse, MeetInfo.class);

            //then
            assertThat(response.getUseYn()).isEqualTo(Flag.N);
        }

        @Test
        @DisplayName("모임 삭제 실패")
        void meetRemoveFail() throws Exception {
            //given
            final Long invalidMeetId = -1L;

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.delete("/meets/{meetId}", invalidMeetId))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andReturn();
        }
    }

    private MvcResult 모임_생성_성공(MeetCreateRequest request) throws Exception {
        String param = objectMapper.writeValueAsString(request);

        return mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                        .content(param)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    private MvcResult 모임_생성_성공() throws Exception {
        return 모임_생성_성공(MeetCreateRequest.builder()
                .name("name")
                .description("description")
                .build());
    }

    private <T> T 응답값을_객체에_매핑함(MvcResult mvcResult, Class<T> type) throws Exception {
        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), type);
    }
}
