package com.moiming.meet.domain;

import com.moiming.core.Flag;
import com.moiming.core.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Builder
@Table(name = "MEET_INFO")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetInfo extends BaseEntity {
    @Id
    @Comment("모임 ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEET_ID", nullable = false)
    private Long meetId;

    @Size(max = 64)
    @NotNull
    @Comment("모임 이름")
    @Column(name = "NAME")
    private String name;

    @Size(max = 512)
    @Comment("모임 설명")
    @Column(name = "DESCRIPTION")
    private String description;

    @Comment("모임 생성일")
    @Column(name = "CREATE_DATE")
    private LocalDate createDate;

    @Comment("사용 여부")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "USE_YN", nullable = false, columnDefinition = "char(1)")
    private Flag useYn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetInfo meetInfo = (MeetInfo) o;
        return Objects.equals(meetId, meetInfo.meetId) && Objects.equals(name, meetInfo.name) && Objects.equals(description, meetInfo.description) && Objects.equals(createDate, meetInfo.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meetId, name, description, createDate);
    }

    /**
     * 모임 사용 여부를 N으로 바꿉니다.
     */
    public void meetRemove() {
        this.useYn = Flag.N;
    }
}
