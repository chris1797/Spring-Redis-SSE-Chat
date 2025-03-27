package com.base.demo.domain.message.service.subscribe;

import com.base.demo.config.annotations.TopicName;
import com.base.demo.domain.message.entity.MessageContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;


/*
Publish 된 메시지를 받아서 처리하는 클래스, pub된 메세지를 해당 클래스에서 받아 로깅, 비즈니스 로직 처리 등을 하는 역할.
 */
@Slf4j
@Service
@TopicName("chat")
@RequiredArgsConstructor
public class RedisChatSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations stompSendingOperations;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
            MessageContent messageContent = objectMapper.readValue(messageBody, MessageContent.class);
            log.info("Message received: {}", messageContent);

            stompSendingOperations.convertAndSend("/topic/messages/1", messageContent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
