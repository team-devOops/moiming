package com.moiming.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String EMAIL_AT = "@";

    @Getter
    private String id;

    @Getter
    private String domain;

    @Getter
    private String address;

    private Email(String email) {
        validate(email);

        String[] split = email.split(EMAIL_AT);
        this.id = split[0];
        this.domain = split[1];
        this.address = email;
    }

    private void validate(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("이메일 주소가 비어있습니다..");
        }

        if (!EMAIL_PATTERN.matcher(email).find()) {
            throw new IllegalArgumentException("잘못된 이메일 형식입니다.");
        }
    }


    public static Email of(String email) {
        return new Email(email);
    }
}
