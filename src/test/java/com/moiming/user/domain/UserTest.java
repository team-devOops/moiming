package com.moiming.user.domain;

import com.moiming.user.domain.Email;
import com.moiming.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDate;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;



@DataJpaTest
class UserTest {

    private Email collectEmail;
    @BeforeEach
    void setUp() {
        collectEmail = Email.of("kbh058@naver.com");
    }

    @DisplayName("유저의 이름은 비어 있으면 안된다")
    @NullAndEmptySource
    @ParameterizedTest
    void noNameUser(String name) {
        assertThatIllegalArgumentException().isThrownBy(() ->
                User.createUser("kbh052", collectEmail, name, "1234", LocalDate.of(1995, 8, 2)));

    }

    @DisplayName("유저의 아이디는 비어 있으면 안된다")
    @NullAndEmptySource
    @ParameterizedTest
    void noIdUser(String id) {
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.createUser(id, collectEmail, "name", "1234", LocalDate.of(1995, 8, 2)));
    }

    @DisplayName("유저의 비밀번호는 비어 있으면 안된다")
    @NullAndEmptySource
    @ParameterizedTest
    void noPasswordUser(String password) {
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.createUser("kbh052", collectEmail, "name", password, LocalDate.of(1995, 8, 2)));
    }

    @DisplayName("유저의 생일은 비어 있으면 안된다")
    @Test
    void noBirthDateUser() {
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.createUser("kbh052", collectEmail, "name", "1234", null));
    }

    @DisplayName("유저의 이메일은 비어 있으면 안된다")
    @Test
    void noEmailUser() {
        assertThatIllegalArgumentException().isThrownBy(()
                -> User.createUser("kbh052", (Email) null, "name", "1234", LocalDate.now()));
    }



}