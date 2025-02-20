package com.base.demo.config;

import com.base.demo.message.service.subscribe.RedisChatSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

    /**
     * 스프링이 Redis의 pub/sub 기능을 사용할 수 있도록 ChannelTopic을 빈으로 등록
     * "chatroom" 이라는 topic으로 메시지를 발행하고 구독할 수 있음
     */
    @Bean(name = "chat")
    public ChannelTopic chatTopic () {
        return new ChannelTopic("chat");
    }

    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory,
                                                              RedisChatSubscriber messageSubscriber
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        /*
         * RedisConnectionFactory는 Redis 서버와의 연결을 설정하는 인터페이스로, Redis 서버와의 연결을 설정하는데 사용
         */
        container.setConnectionFactory(connectionFactory);

        /**
         * Redis가 해당 topic으로 발행된 메시지를 수신하여 처리할 수 있도록 설정
         * 등록된 messageSubscriber의 onMessage 메소드를 호출하여 메시지를 처리
         */
        container.addMessageListener(new MessageListenerAdapter(messageSubscriber, "onMessage"), chatTopic()); // chat

        return container;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return template;
    }
}

