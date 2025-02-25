package com.base.demo.message.service.subscribe;

import com.base.demo.config.annotations.TopicName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@TopicName("order")
@RequiredArgsConstructor
public class RedisOrderSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // order topic에 발행된 메시지를 수신하여 부가 로직 구현
    }
}
