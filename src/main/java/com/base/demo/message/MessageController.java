package com.base.demo.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    @Qualifier(value = "chat")
    private ChannelTopic channelTopic;
    private final RedisTemplate<?, ?> redisTemplate;


    @MessageMapping("/chat/{roomNo}")
    public void send(@DestinationVariable String roomNo) {
        log.info("/chat/{}, Topic :: {}", roomNo, channelTopic);
    }

}
