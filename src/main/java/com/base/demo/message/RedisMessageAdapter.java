package com.base.demo.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisMessageAdapter implements MessageListener {


    @Override
    public void onMessage(Message message, byte[] pattern) {
    }
}
