package com.moiming.user.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_SEQ")
    @Comment("유저 KEY")
    private Long seq;

    @NotNull
    @Size(min = 4, max = 20)
    @Column(name = "USER_ID", unique = true)
    @Comment("유저 아이디")
    private String userId;

    @Embedded
    @NotNull
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "EMAIL", unique = true, nullable = false, columnDefinition = "VARCHAR(50)"))
    )
    @Comment("유저 이메일")
    private Email email;

    @NotNull
    @Size(max = 20)
    @Column(name = "NAME")
    @Comment("유저 이름")
    private String name;

    @NotNull
    @Size(max = 40)
    @Column(name = "PASSWORD")
    @Comment("패스워드")
    private String password;

    @NotNull
    @Column(name = "BIRTH_DATE")
    @Comment("생일")
    private LocalDate birthDate;

    @NotNull
    @Size(max = 1)
    @Column(name = "USE_YN")
    @Comment("사용여부")
    private String useYn;

    @NotNull
    @Size(max = 1)
    @Column(name = "AUTH_YN")
    @Comment("이메일 인증 여부")
    private String authYn;

    @Builder
    private User(Long seq, String userId, Email email, String name, String password, LocalDate birthDate) {
        validateNonEmpty(userId, "유저아이디");
        validateNonEmpty(password, "패스워드");
        validateNonEmpty(name, "이름");
        validateNonNull(birthDate, "생년월일");

        this.seq = seq;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.useYn = "Y";
        this.authYn = "N";
    }

    private void validateNonEmpty(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(fieldName + "이(가) 비어있습니다.");
        }
    }

    private <T> void validateNonNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + "이(가) 비어있습니다.");
        }
    }
}
