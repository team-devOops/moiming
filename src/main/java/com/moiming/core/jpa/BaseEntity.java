package com.moiming.core.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@MappedSuperclass
@EnableJpaAuditing
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @NotNull
    @CreatedDate
    @Comment("생성일")
    @Column(name = "REG_DATE", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Comment("수정일")
    @Column(name = "UPD_DATE")
    private LocalDateTime updDate;

}
