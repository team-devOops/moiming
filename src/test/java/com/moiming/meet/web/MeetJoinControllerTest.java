package com.moiming.meet.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moiming.meet.domain.Level;
import com.moiming.meet.dto.MeetJoinRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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

            String param = objectMapper.writeValueAsString(request);

            //when & then
            mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                            .content(param)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated());

            //TODO: 가입 상세 조회 구현시 데이터 비교 추가 필요
        }
    }
}