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
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.createUser("kbh052", collectEmail, name, "1234", LocalDate.of(1995, 8, 2)));

    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("유저의 아이디는 비어 있으면 안된다")
    void noIdUser(String id) {
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.createUser(id, collectEmail, "name", "1234", LocalDate.of(1995, 8, 2)));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("유저의 비밀번호는 비어 있으면 안된다")
    void noPasswordUser(String password) {
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.createUser("kbh052", collectEmail, "name", password, LocalDate.of(1995, 8, 2)));
    }

    @Test
    @DisplayName("유저의 생일은 비어 있으면 안된다")
    void noBirthDateUser() {
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.createUser("kbh052", collectEmail, "name", "1234", null));
    }

    @Test
    @DisplayName("유저의 이메일은 비어 있으면 안된다")
    void noEmailUser() {
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.createUser("kbh052", (Email) null, "name", "1234", LocalDate.now()));
    }
}
