package com.moiming.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Comment("유저 KEY")
    @Column(name = "USER_SEQ")
    private Long seq;

    @Comment("유저 아이디")
    @Column(name = "ID", unique = true, nullable = false, columnDefinition = "VARCHAR(20)")
    private String id;

    @Comment("유저 이메일")
    @Column(name = "EMAIL", unique = true, nullable = false, columnDefinition = "VARCHAR(100)")
    @Embedded
    private Email email;

    @Comment("유저 이름")
    @Column(name = "NAME", nullable = false, columnDefinition = "VARCHAR(20)")
    private String name;

    @Comment("패스워드")
    @Column(name = "PASSWORD", nullable = false, columnDefinition = "VARCHAR(40)")
    private String password;

    @Comment("생일")
    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

    @Comment("사용여부")
    @Column(name = "USE_YN", nullable = false, columnDefinition = "CHAR(1)")
    private String useYn;

    @Comment("이메일 인증 여부")
    @Column(name = "AUTH_YN", nullable = false, columnDefinition = "CHAR(1)")
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

    public static User createUser(String id, Email email, String name, String password, LocalDate birthDate) {
        return new User(id, email, name, password, birthDate);
    }

}
