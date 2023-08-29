package com.moiming.meet.domain;

import com.moiming.core.Flag;
import com.moiming.core.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEET_JOIN_USER")
public class MeetJoinUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEET_JOIN_ID", nullable = false)
    private Long meetJoinId;

    @ManyToOne
    @JoinColumn(name = "MEET_ID")
    @Comment("모임 KEY")
    private MeetInfo meetInfo;

    //TODO: 회원 엔티티 생기면 수정 필요
    @Comment("회원 KEY")
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @Comment("닉네임")
    @Column(name = "NICKNAME", nullable = false, columnDefinition = "varchar(16)")
    private String nickname;

    @Comment("등급")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "LEVEL", nullable = false, columnDefinition = "varchar(16)")
    private Level level;

    @Comment("가입 여부")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "USE_YN", nullable = false, columnDefinition = "char(1)")
    private Flag useYn;

    @Comment("모임 가입일")
    @Column(name = "JOIN_DATE")
    private LocalDate joinDate;

    /**
     * 모임 탈퇴시 가입 여부 'N' 처리
     */
    public void leave() {
        useYn = Flag.N;
    }
}
