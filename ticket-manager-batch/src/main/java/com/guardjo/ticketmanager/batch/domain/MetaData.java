package com.guardjo.ticketmanager.batch.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class MetaData {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime modifiedTime;
}
