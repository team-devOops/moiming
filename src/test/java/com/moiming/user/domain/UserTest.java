package com.moiming.user.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;


class UserTest {

    private Email collectEmail;

    @BeforeEach
    void setUp() {
        collectEmail = Email.of("kbh058@naver.com");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("유저의 이름은 비어 있으면 안된다")
    void noNameUser(String name) {
        assertThatIllegalArgumentException().isThrownBy(
                () -> User.builder().birthDate(LocalDate.of(1995, 8, 2))
                        .email(collectEmail)
                        .userId("kbh052")
                        .name(name)
                        .password("1234")
                        .build());

    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("유저의 아이디는 비어 있으면 안된다")
    void noIdUser(String userId) {
        assertThatIllegalArgumentException().isThrownBy(
                () -> User.builder().birthDate(LocalDate.of(1995, 8, 2))
                        .email(collectEmail)
                        .userId(userId)
                        .name("name")
                        .password("1234")
                        .build());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("유저의 비밀번호는 비어 있으면 안된다")
    void noPasswordUser(String password) {
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.builder().birthDate(LocalDate.of(1995, 8, 2))
                .email(collectEmail)
                .userId("kbh052")
                .name("name")
                .password(password)
                .build());
    }

    @Test
    @DisplayName("유저의 생일은 비어 있으면 안된다")
    void noBirthDateUser() {
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.builder().birthDate(null)
                .email(collectEmail)
                .userId("kbh052")
                .name("name")
                .password("1234")
                .build());
    }
}
