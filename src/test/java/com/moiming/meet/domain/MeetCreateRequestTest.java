package com.moiming.meet.domain;

import com.moiming.meet.dto.MeetCreateRequest;
import com.moiming.meet.dto.MeetValidateMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Set;

import static org.assertj.core.api.SoftAssertions.assertSoftly;


class MeetCreateRequestTest {

    private static Validator validator;

    @BeforeEach
    void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Nested
    @DisplayName("모임명 유효성 검사")
    class validationName {
        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("모임명이 null 혹은 빈값 일 때 오류")
        void nameIsNull(String input) {
            //given
            MeetCreateRequest request = MeetCreateRequest.builder()
                    .name(input)
                    .description("description")
                    .build();

            //when

            Set<ConstraintViolation<MeetCreateRequest>> validate = validator.validate(request);
            ConstraintViolation<MeetCreateRequest> result = validate.iterator().next();

            //then
            assertSoftly(softAssertions -> {
                softAssertions.assertThat(validate.size()).isNotZero();
                softAssertions.assertThat(result.getMessage()).isEqualTo(MeetValidateMessage.NAME_IS_NOT_NULL);
            });
        }
    }

    @Test
    @DisplayName("모임명이 64자 이상 작성되었을 경우 오류")
    void nameSizeIsOver() {
        //given
        String name = "사랑과 아침이 말 이웃 이런 나는 다 때 까닭입니다. 이름을 가득 나의 라이너 하나에 이름을 밤이 묻힌 거외다. 이국 같이 너무나 봅니다.";

        //when
        MeetCreateRequest request = MeetCreateRequest.builder()
                .name(name)
                .build();

        Set<ConstraintViolation<MeetCreateRequest>> validate = validator.validate(request);
        ConstraintViolation<MeetCreateRequest> result = validate.iterator().next();

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(validate.size()).isNotZero();
            softAssertions.assertThat(result.getMessage()).isEqualTo(MeetValidateMessage.NAME_SIZE_INVALID);
        });
    }

    @Test
    @DisplayName("MeetCreateRequest 유효성 검사 통과")
    void meetCreateRequestValid() {
        //given
        String name = "name";
        String description = "description";

        //when
        MeetCreateRequest request = MeetCreateRequest.builder()
                .name(name)
                .description(description)
                .build();

        Set<ConstraintViolation<MeetCreateRequest>> validate = validator.validate(request);

        //then
        Assertions.assertThat(validate.size()).isZero();
    }
}
