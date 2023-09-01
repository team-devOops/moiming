package com.moiming.user.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moiming.config.IntegrationTest;
import com.moiming.user.dto.SignUpRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@IntegrationTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 성공")
    void signUp() throws Exception {
        //given
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .userId("kbh2")
                .password("패스워드")
                .email("kbh0581@Gmail.com")
                .name("이름")
                .birthDate(LocalDate.now())
                .build();

        //when
        final ResultActions 회원가입_요청 = 회원가입_요청(signUpRequest);

        //then
        회원가입_요청.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 실패 - 아이디 중복")
    void signUpFail() throws Exception {
        //given
        SignUpRequest 회원1 = SignUpRequest.builder()
                .userId("kbh2")
                .password("패스워드")
                .email("kbh0581@Gmail.com")
                .name("이름")
                .birthDate(LocalDate.now())
                .build();

        //given
        이미_회원_가입이_되어진_유저(회원1);

        SignUpRequest 중복된_이메일_회원가입_요청 = SignUpRequest.builder()
                .userId(회원1.userId())
                .password("패스워드")
                .email("kbh0581@Gmail.com")
                .name("이름")
                .birthDate(LocalDate.now())
                .build();

        //when
        final ResultActions 회원가입_요청 = 회원가입_요청(중복된_이메일_회원가입_요청);

        //then
        회원가입_요청.andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 중복")
    void signUpFail2() throws Exception {
        //given
        SignUpRequest 회원1 = SignUpRequest.builder()
                .userId("kbh2")
                .password("패스워드")
                .email("kbh0581@Gmail.com")
                .name("이름")
                .birthDate(LocalDate.now())
                .build();

        //given
        이미_회원_가입이_되어진_유저(회원1);

        SignUpRequest 중복된_이메일_회원가입_요청 = SignUpRequest.builder()
                .userId("kbh3")
                .password("패스워드")
                .email(회원1.email())
                .name("이름")
                .birthDate(LocalDate.now())
                .build();

        //when
        final ResultActions 회원가입_요청 = 회원가입_요청(중복된_이메일_회원가입_요청);

        //then
        회원가입_요청.andDo(print())
                .andExpect(status().isConflict());
    }


    @ParameterizedTest
    @ValueSource(strings = {"123", "123456789012345678901"})
    @NullAndEmptySource
    @DisplayName("회원가입 실패 - 비밀번호가 4자 ~ 20자 이상")
    void signUpFail3(String password) throws Exception {
        //given & when
        회원가입_요청(SignUpRequest.builder()
                .userId("kbh2")
                .password(password)
                .email("kbh0581@Gmail.com")
                .name("이름")
                .birthDate(LocalDate.now())
                .build())

                //then
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "123456789012345678901"})
    @NullAndEmptySource
    @DisplayName("회원가입 실패 - 아이디가 비밀번호가 4자 ~ 20자 이상")
    void signUpFail4(String userId) throws Exception {
        //given & when
        회원가입_요청(SignUpRequest.builder()
                .userId(userId)
                .password("패스워드")
                .email("kbh0581@Gmail.com")
                .name("이름")
                .birthDate(LocalDate.now())
                .build())
                //then
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "123456789012345678901"})
    @NullAndEmptySource
    @DisplayName("회원가입 실패 - 아이디가 4자 ~ 20자 이상")
    void signUpFail5(String userId) throws Exception {
        //given
        회원가입_요청(SignUpRequest.builder()
                .userId(userId)
                .password("패스워드")
                .email("kbh0581@Gmail.com")
                .name("이름")
                .birthDate(LocalDate.now())
                .build())
                //then
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("회원가입 실패 - 이름이 비어있음")
    void signUpFail6(String name) throws Exception {
        회원가입_요청(SignUpRequest.builder()
                .userId("kbh2")
                .password("패스워드")
                .email("kbh0581@Gmail.com")
                .name(name)
                .birthDate(LocalDate.now())
                .build())
                //then
                .andExpect(status().isBadRequest());
        ;
    }

    @Test
    @DisplayName("회원가입 실패 - birthDate가 비어있음")
    void signUpFail7() throws Exception {
        회원가입_요청(SignUpRequest.builder()
                .userId("kbh2")
                .password("패스워드")
                .email("kbh0581@Gmail.com")
                .name("name")
                .birthDate(null)
                .build())
                //then
                .andExpect(status().isBadRequest());
        ;
    }


    @ParameterizedTest
    @ValueSource(strings = {"123", "1234567@89012345678901", "@kbg.com"})
    @NullAndEmptySource
    @DisplayName("회원가입 실패 - 유효하지 않은 이메일")
    void signUpFail8(String email) throws Exception {
        회원가입_요청(SignUpRequest.builder()
                .userId("kbh2")
                .password("패스워드")
                .email(email)
                .name("name")
                .birthDate(null)
                .build())
                //then
                .andExpect(status().isBadRequest());
        ;
    }

    private ResultActions 회원가입_요청(SignUpRequest signUpRequest) throws Exception {
        return mockMvc.perform(post("/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json변환(signUpRequest)))
                .andDo(log())
                .andDo(print());
    }

    private void 이미_회원_가입이_되어진_유저(SignUpRequest signUpRequest) throws Exception {
        회원가입_요청(signUpRequest)
                .andExpect(status().isOk());
    }

    private String json변환(Object payload) throws JsonProcessingException {
        return objectMapper.writeValueAsString(payload);
    }

}
