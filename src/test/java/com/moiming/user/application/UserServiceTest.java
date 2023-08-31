package com.moiming.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.moiming.core.exception.DuplicationException;
import com.moiming.user.domain.Email;
import com.moiming.user.domain.User;
import com.moiming.user.domain.UserRepository;
import com.moiming.user.dto.SignUpRequest;
import com.moiming.user.stub.UserRepositoryStub;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryStub();
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("회원가입 성공")
    void signUp() {
        //given
        SignUpRequest request = SignUpRequest.builder()
                .userId("1")
                .name("name")
                .password("password")
                .email("asdsadsad02@gmial.com")
                .birthDate(LocalDate.of(1995, 1, 1))
                .build();

        //when
        final Long seq = userService.signUp(request);

        //then
        assertThat(seq).isEqualTo(1L);
    }

    @Test
    @DisplayName("회원가입 실패 - 아이디 중복")
    void signUpFail() {
        //given
        final User 저장된_유저 = 저장된_유저(User.builder()
                .userId("1")
                .name("name")
                .password("password")
                .birthDate(LocalDate.of(1995, 1, 1))
                .email(Email.of("kbh05@sadasd.com"))
                .build());

        SignUpRequest request = SignUpRequest.builder()
                .userId(저장된_유저.getUserId())
                .name("name")
                .password("password")
                .email("kbh05@sadasd.com")
                .birthDate(LocalDate.of(1995, 1, 1))
                .build();

        //when & then
        assertThatThrownBy(() -> userService.signUp(request))
                .isInstanceOf(DuplicationException.class);
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 중복")
    void signUpFail2() {
        //given
        final User 저장된_유저 = 저장된_유저(User.builder()
                .userId("1")
                .name("name")
                .password("password")
                .birthDate(LocalDate.of(1995, 1, 1))
                .email(Email.of("kbh232@gmail.com"))
                .build());

        SignUpRequest request = SignUpRequest.builder()
                .userId("2")
                .name("name")
                .password("password")
                .email(저장된_유저.getEmail().getValue())
                .birthDate(LocalDate.of(1995, 1, 1))
                .build();

        //when & then
        assertThatThrownBy(() -> userService.signUp(request))
                .isInstanceOf(DuplicationException.class);

    }

    private User 저장된_유저(User user) {
        return userRepository.save(user);
    }

}