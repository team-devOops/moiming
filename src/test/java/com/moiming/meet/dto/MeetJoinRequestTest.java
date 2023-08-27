package com.moiming.meet.dto;

import com.moiming.meet.domain.Level;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;

class MeetJoinRequestTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Nested
    @DisplayName("모임 ID 검증")
    class meetId {
        @ParameterizedTest
        @NullSource
        @ValueSource(longs = {-1, 0})
        @DisplayName("잘못된 형식의 모임ID 입력시 실패")
        void meetIdOneLessFail(Long meetId) {
            //when
            final MeetJoinRequest request = MeetJoinRequest.builder()
                    .meetId(meetId)
                    .userSeq(1234L)
                    .nickname("nickname")
                    .level(Level.ADMIN)
                    .build();

            Set<ConstraintViolation<MeetJoinRequest>> violations = validator.validate(request);
            ConstraintViolation<MeetJoinRequest> result = violations.iterator().next();

            //then
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isNotZero();
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("meetId");
            });
        }
    }

    @Nested
    @DisplayName("닉네임 검증")
    class nickname {
        @ParameterizedTest
        @ValueSource(strings = {"가나다라마바사아자차카파타하가나다라마"})
        @NullAndEmptySource
        @DisplayName("닉네임 null, 1자 미만, 혹은 16자 이상이면 실패")
        void nicknameSize(String nickname) {
            //when
            final MeetJoinRequest request = MeetJoinRequest.builder()
                    .meetId(1L)
                    .userSeq(1234L)
                    .nickname(nickname)
                    .level(Level.ADMIN)
                    .build();

            Set<ConstraintViolation<MeetJoinRequest>> violations = validator.validate(request);
            ConstraintViolation<MeetJoinRequest> result = violations.iterator().next();

            //then
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isNotZero();
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("nickname");
            });
        }
    }

    @Nested
    @DisplayName("등급 검증")
    class level {
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"AAA", "ㄱㄴㄷ"})
        @DisplayName("빈값, 존재하지 않는 값 넣으면 실패")
        void levelIsNotNull(String level) {
            //when
            final MeetJoinRequest request = MeetJoinRequest.builder()
                    .meetId(1L)
                    .userSeq(1234L)
                    .nickname("nickname")
                    .level(Level.fromInput(level))
                    .build();

            Set<ConstraintViolation<MeetJoinRequest>> violations = validator.validate(request);
            ConstraintViolation<MeetJoinRequest> result = violations.iterator().next();

            //then
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(violations).size().isNotZero();
                softAssertions.assertThat(result.getPropertyPath().toString()).isEqualTo("level");
            });
        }
    }

    @Test
    @DisplayName("성공적으로 생성 성공")
    void success() {
        MeetJoinRequest.builder()
                .meetId(1L)
                .userSeq(999L)
                .nickname("nickname")
                .level(Level.ADMIN)
                .build();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }
}
