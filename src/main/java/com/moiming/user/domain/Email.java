package com.moiming.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Email {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String EMAIL_AT = "@";

    @Transient
    private String id;

    @Transient
    private String domain;

    private String value;

    private Email(String email) {
        validate(email);

        String[] split = email.split(EMAIL_AT);
        this.id = split[0];
        this.domain = split[1];
        this.value = email;
    }

    public static Email of(String email) {
        return new Email(email);
    }

    private void validate(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("이메일 주소가 비어있습니다..");
        }

        if (!EMAIL_PATTERN.matcher(email).find()) {
            throw new IllegalArgumentException("잘못된 이메일 형식입니다.");
        }
    }
}
