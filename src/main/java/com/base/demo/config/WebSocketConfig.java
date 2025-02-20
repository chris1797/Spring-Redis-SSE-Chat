package com.base.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocketMessageBrokerConfigurer 인터페이스를 구현하여 STOMP 메시징을 사용하는 WebSocket을 구성
 *
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker // WebSocket 서버를 활성화
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;


    // STOMP 프로토콜을 사용하는 웹 소켓 엔드포인트를 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // ex) localhost:8080/ws/chat
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS().setHeartbeatTime(5000L);

        // ex) localhost:8080/ws/sse
        registry.addEndpoint("/ws/sse").setAllowedOriginPatterns("*").withSockJS().setHeartbeatTime(5000L);
    }


    /**
     * 메시지 브로커 구성, Spring의 SimpleBroker를 사용하여 메시지들을 클라이언트로 브로드캐스트
     * 클라이언트에서는 /topic, /eventStream 으로 시작하는 메시지를 구독할 수 있음
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        /*
        서버 -> 클라이언트
        해당 메시지 브로커가 "/topic/~", "/eventStream/~" 로 시작하는 메시지를 클라이언트로 전파
        클라이언트에서 /topic, /eventStream 으로 시작하는 메시지를 구독 및 수신할 수 있음
         */
        registry.enableSimpleBroker("/topic", "/eventStream");

        /*
        클라이언트 -> 서버
        클라이언트에서 "/app/~", "/sse/~" prefix로 시작하는 메시지를 발행하면 @MessageMapping으로 전달
        해당 prefix에 등록된 메시지들만 @MessageMapping에서 처리할 수 있음
         */
        registry.setApplicationDestinationPrefixes("/app", "/sse");
    }

    /**
     * 클라이언트로부터 들어오는 메세지를 가로채어 처리하는 핸들러를 등록한다. (StompHandler)
     * 여기서 주입받은 StompHandler 를 등록한다.
     * @param registration ChannelRegistration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        // 주입받는 Handler는 반드시 ChannelInterceptor를 구현해야 한다.
        registration.interceptors(stompHandler);
    }
}
