package com.base.demo.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    // 전체 플로우 : 클라이언트 → WebSocket 엔드포인트 (/ws/chat) → `preSend()` → 메시지 브로커 → (구독된 클라이언트)

    /**
     * 메세지 전달 전 메세지를 가로채어 처리하는 메서드
     * <p>클라이언트에서 받은 메세지를 preSend()에서 중간 처리를 하고 return 된 Message<?>는 Spring 내부의 메시지 처리 체인(message processing chain, 즉)으로 전달된다.</p>
     * @param message 메시지
     * @param channel 메시지 전달 채널 인터페이스로, 메시지가 오가는 "파이프라인" 역할을 하며, 특정 메시지를 어디로 보낼지 정의된 인터페이스
     * @return Message 메시지
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("클라이언트로부터 받음 :: message {}", message);

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand(); // STOMP 명령어 확인 (CONNECT, SUBSCRIBE, SEND 등)
        if (command == null) return message;

        switch (command) {
            case CONNECTED -> log.info("클라이언트가 연결되었습니다.");
            case SUBSCRIBE -> {
                String destination = accessor.getDestination();
                log.info("클라이언트가 구독했습니다. 구독한 대상 : {}", destination);
            }
            case SEND -> log.info("클라이언트가 메시지를 보냈습니다.");
            case DISCONNECT -> log.info("클라이언트가 연결을 끊었습니다.");
        }

        return message;

    }
}
