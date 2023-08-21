package com.moiming.user.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_SEQ")
    @Comment("유저 KEY")
    private Long seq;

    @NotNull
    @Size(min = 4, max = 20)
    @Column(name = "ID", unique = true)
    @Comment("유저 아이디")
    private String id;

    @NotNull
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "EMAIL", unique = true, columnDefinition = "VARCHAR(50)", nullable = false))
    )
    private Email email;

    @NotNull
    @Size(max = 20)
    @Column(name = "NAME")
    @Comment("유저 이름")
    private String name;

    @NotNull
    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(40)")
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

    private User(String id, Email email, String name, String password, LocalDate birthDate) {
        validateId(id);
        validatePassword(password);
        validateName(name);
        validateEmail(email);
        validateBirthDate(birthDate);

        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.birthDate = birthDate;
        this.useYn = "Y";
        this.authYn = "N";
    }

    public static User createUser(String id, Email email, String name, String password, LocalDate birthDate) {
        return new User(id, email, name, password, birthDate);
    }

    public static User createUser(String id, String email, String name, String password, LocalDate birthDate) {
        return new User(id, Email.of(email), name, password, birthDate);
    }


    private void validateId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("아이디가 비어있습니다.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호가 비어있습니다.");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("이름이 비어있습니다.");
        }
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate == null) {
            throw new IllegalArgumentException("생일이 비어있습니다.");
        }
    }

    private void validateEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("이메일이 비어있습니다.");
        }
    }

}
