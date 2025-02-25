package com.base.demo.message;

import com.base.demo.message.entity.MessageContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final Map<String, ChannelTopic> channelTopic;
    private final RedisTemplate<?, ?> redisTemplate;


    @MessageMapping("/chat/{roomNo}")
    public void send(@DestinationVariable String roomNo, @Payload MessageContent messageContent) {
        redisTemplate.convertAndSend(channelTopic.get("chat").getTopic(), roomNo);
    }

    @MessageMapping("/chat/send")
    public void send(@Payload MessageContent messageContent) {
        // 누군가 채팅 메시지를 발행(전송) 하면 Redis에 해당 topic(channel)로 publish
        redisTemplate.convertAndSend(channelTopic.get("chat").getTopic(), messageContent);
    }

}
