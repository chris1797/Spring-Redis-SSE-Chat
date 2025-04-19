package com.base.demo.domain.message.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MessageContent implements Serializable {

    private Long id;
    private Long roomId;
    private Long userId;
    private String message;

    private LocalDateTime createAt;

}