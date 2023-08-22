package com.moiming.meet.domain;

import com.moiming.core.Flag;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "MEET_JOIN_USER")
public class MeetJoinUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEET_JOIN_SEQ", nullable = false)
    private Long meetSeq;

    @ManyToOne
    @JoinColumn(name = "MEET_SEQ")
    @Comment("모임 KEY")
    private MeetInfo meetInfo;

    //TODO: 회원 엔티티 생기면 수정 필요
    @Comment("회원 KEY")
    @Column(name = "DESCRIPTION")
    private Long userSeq;

    @Comment("닉네임")
    @Column(name = "NICKNAME", nullable = false, columnDefinition = "varchar(16)")
    private String nickname;

    @Comment("등급")
    @Column(name = "LEVEL", nullable = false, columnDefinition = "varchar(16)")
    private String level;

    @Comment("탈퇴 여부")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "USE_YN", nullable = false, columnDefinition = "char(1)")
    private Flag useYn;
}