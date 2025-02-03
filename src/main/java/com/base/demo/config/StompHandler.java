package com.base.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {


    /**
     * 메세지 전달 전 처리
     * @param message 메시지
     * @param channel 채널
     * @return Message 메시지
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("preSend");
        return message;
    }
}
