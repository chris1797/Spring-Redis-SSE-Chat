package com.base.demo.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    @Qualifier(value = "chat")
    private ChannelTopic channelTopic;
    private final RedisTemplate<?, ?> redisTemplate;

    /**
     * 클라이언트가 /chat/{roomNo}로 메시지를 보내면 해당 roomNo에 해당하는 topic으로 메시지를 발행
     * @param roomNo 채팅방 번호
     */
    @MessageMapping("/chat/{roomNo}")
    public void send(@DestinationVariable String roomNo) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), roomNo);
        log.info("/chat/{}, Topic :: {}", roomNo, channelTopic);
    }

}
