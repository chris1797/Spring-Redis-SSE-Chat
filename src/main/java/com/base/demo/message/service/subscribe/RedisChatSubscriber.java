package com.base.demo.message.service.subscribe;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisChatSubscriber implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {

    }
}
