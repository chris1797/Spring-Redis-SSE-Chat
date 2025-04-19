package com.base.demo.config;

import com.base.demo.config.annotations.TopicName;
import com.base.demo.domain.message.service.subscribe.RedisChatSubscriber;
import com.base.demo.domain.message.service.subscribe.RedisOrderSubscriber;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisConfig {

    private final ObjectMapper objectMapper;

    /**
     * 스프링이 Redis의 pub/sub 기능을 사용할 수 있도록 ChannelTopic을 빈으로 등록
     * "chatroom" 이라는 topic으로 메시지를 발행하고 구독할 수 있음
     */
    @Bean
    public Map<String, ChannelTopic> redisTopics () {
        Map<String, ChannelTopic> topics = new HashMap<>();
        topics.put("chat", new ChannelTopic("chat"));
        topics.put("order", new ChannelTopic("order"));

        return topics;
    }

    @Bean
    public List<MessageListenerAdapter> messageListeners (
            RedisChatSubscriber messageSubscriber,
            RedisOrderSubscriber orderSubscriber
    ) {
        return List.of(
                new MessageListenerAdapter(messageSubscriber, "onMessage"),
                new MessageListenerAdapter(orderSubscriber, "onMessage")
        );
    }

    /**
     * RedisConnectionFactory는 Redis 서버와의 연결을 설정하는 인터페이스로, Redis 서버와의 연결을 설정하는데 사용
     * LettuceConnectionFactory는 Lettuce 클라이언트를 사용하여 Redis 서버와의 연결을 설정하는 구현체
     */
    @Bean
    public RedisConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory("localhost", 6379);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory,
                                                              List<MessageListenerAdapter> listeners,
                                                              Map<String, ChannelTopic> topics
    ) {
        // RedisConnectionFactory는 Redis 서버와의 연결을 설정하는 인터페이스로, Redis 서버와의 연결을 설정하는데 사용
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        /*
         * Redis가 해당 topic으로 발행된 메시지를 수신하여 처리할 수 있도록 설정
         * 등록된 messageSubscriber의 onMessage 메소드를 호출하여 메시지를 처리
         */
//        container.addMessageListener(new MessageListenerAdapter(messageSubscriber, "onMessage"), chatTopic()); // chat
        listeners.forEach(listener -> {
            String topicName = Objects.requireNonNull(
                    listener.getDelegate(), "Delegate is null")
                    .getClass().getAnnotation(TopicName.class).value();

            ChannelTopic topic = topics.get(topicName);
            if (topic != null) container.addMessageListener(listener, topic);
        });

        return container;
    }

    /**
     * 🔹RedisTemplate은 Redis 서버와의 상호작용을 위한 템플릿 클래스
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }
}

