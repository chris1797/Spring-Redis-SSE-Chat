package com.base.demo.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RedisMessage implements Serializable {

    private Long id;
    private Long roomId;
    private Long userId;
    private String message;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createAt;

    public RedisMessage(Long id, Long roomId, Long userId, String message, LocalDateTime createAt) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.message = message;
        this.createAt = createAt;

    }
}
