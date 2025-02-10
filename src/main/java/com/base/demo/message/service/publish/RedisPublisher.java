package com.base.demo.message.service.publish;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisPublisher {

    private final RedisTemplate<String, String> redisTemplate;
    private final ChannelTopic chatTopic;


    public RedisPublisher(RedisTemplate<String, String> redisTemplate,
                          @Qualifier("chat") ChannelTopic chatTopic) {
        this.redisTemplate = redisTemplate;
        this.chatTopic = chatTopic;
    }


    public void chatPublish(String message) {
        log.info("Publishing message: {}", message);
        val result = redisTemplate.convertAndSend(chatTopic.getTopic(), message);
        log.info("Published message: {}", result);
    }



}
