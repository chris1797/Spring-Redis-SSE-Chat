package com.base.demo.message.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Message implements Serializable {

    private Long id;

    private Long roomId;
    private Long userId;
    private String message;

    @CreatedDate
    private LocalDateTime createAt;

}