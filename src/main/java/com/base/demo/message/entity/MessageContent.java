package com.base.demo.message.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class MessageContent implements Serializable{

    private Long id;

    private Long roomId;
    private Long userId;
    private String message;

    private LocalDateTime createAt;

}