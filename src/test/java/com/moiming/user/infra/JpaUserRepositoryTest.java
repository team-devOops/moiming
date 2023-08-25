package com.moiming.user.infra;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.moiming.user.domain.Email;
import com.moiming.user.domain.User;
import com.moiming.user.domain.UserRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class JpaUserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Test
    @DisplayName("유저 저장 성공")
    void saveUser() {
        //given
        LocalDate now = LocalDate.now();
        User user1 = User.builder()
                .id("1")
                .email(Email.of("email@naver.com"))
                .name("name")
                .password("password")
                .birthDate(now)
                .build();

        //when
        final User savedUser = userRepository.save(user1);

        //then
        SoftAssertions.assertSoftly(it -> {
            it.assertThat(savedUser.getId()).isEqualTo("1");
            it.assertThat(savedUser.getEmail().getValue()).isEqualTo("email@naver.com");
            it.assertThat(savedUser.getPassword()).isEqualTo("password");
            it.assertThat(savedUser.getName()).isEqualTo("name");
            it.assertThat(savedUser.getBirthDate()).isEqualTo(now);
            it.assertThat(savedUser.getSeq()).isNotNull();
        });
    }

    @Test
    @DisplayName("이메일이 존재하는지 조회한다.")
    void existsByEmail() {
        //given
        final User user = 저장된_유저(User.builder()
                .id("1")
                .email(Email.of("email@naver.com"))
                .name("name")
                .password("password")
                .birthDate(LocalDate.now())
                .build());

        //when
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());

        //then
        assertThat(findUser).isPresent();
        assertThat(findUser.get().getEmail().getValue()).isEqualTo(user.getEmail().getValue());
    }

    private User 저장된_유저(User user1) {
        return userRepository.save(user1);
    }

}