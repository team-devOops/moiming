package com.moiming.meet.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "MEET_INFO")
public class MeetInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEET_SEQ", nullable = false)
    private Long meetSeq;

    @Comment("모임 이름")
    @Column(name = "NAME", nullable = false, columnDefinition = "varchar(64)")
    private String name;

    @Comment("모임 설명")
    @Column(name = "DESCRIPTION", columnDefinition = "varchar(512)")
    private String description;
}
