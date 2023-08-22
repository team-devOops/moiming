package com.moiming.meet.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@Table(name = "MEET_INFO")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEET_SEQ", nullable = false)
    private Long meetSeq;

    @Size(max = 64)
    @NotNull
    @Comment("모임 이름")
    @Column(name = "NAME")
    private String name;

    @Size(max = 512)
    @Comment("모임 설명")
    @Column(name = "DESCRIPTION")
    private String description;
}
