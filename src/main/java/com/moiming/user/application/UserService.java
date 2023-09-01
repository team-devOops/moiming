package com.moiming.user.application;

import static com.moiming.user.domain.UserErrorMessage.EXIST_EMAIL;
import static com.moiming.user.domain.UserErrorMessage.EXIST_ID;

import com.moiming.core.exception.DuplicationException;
import com.moiming.user.domain.Email;
import com.moiming.user.domain.User;
import com.moiming.user.domain.UserRepository;
import com.moiming.user.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입을 진행합니다.
     */
    public Long signUp(SignUpRequest request) {
        log.info("signUp request: {}", request);

        User singUpUser = User.builder().userId(request.userId())
                        .password(request.password())
                        .email(Email.of(request.email()))
                        .name(request.name())
                        .birthDate(request.birthDate())
                        .build();


        userRepository.findByUserId(singUpUser.getUserId()).ifPresent(user -> {
            throw new DuplicationException(EXIST_ID.getMessage());
        });

        userRepository.findByEmail(singUpUser.getEmail()).ifPresent(user -> {
            throw new DuplicationException(EXIST_EMAIL.getMessage());
        });

        return userRepository.save(singUpUser)
                .getSeq();
    }

}
