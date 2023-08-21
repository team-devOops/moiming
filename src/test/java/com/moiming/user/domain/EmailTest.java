package com.moiming.user.domain;


import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class EmailTest {

    @DisplayName("이메일 주소가 비어있으면 안된다.")
    @NullAndEmptySource
    @ParameterizedTest
    void notEmpty(String email) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Email.of(email));
    }

    @DisplayName("이메일 형식이 잘못되면 안된다.")
    @ParameterizedTest
    @CsvSource(value = {"abc", "abc@abc", "abc@abc."})
    void validEmail(String email) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Email.of(email));
    }

    @DisplayName("이메일이 생성이 된다.")
    @Test
    void createEmail() {
        // given
        String emailString = "kbh052@gmail.com";

        //when
        Email email = Email.of(emailString);

        //then
        assertSoftly(softly -> {
            softly.assertThat(email.getId()).isEqualTo("kbh052");
            softly.assertThat(email.getDomain()).isEqualTo("gmail.com");
            softly.assertThat(email.getValue()).isEqualTo(emailString);
        });
    }
}