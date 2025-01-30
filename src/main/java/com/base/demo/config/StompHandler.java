package com.base.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.ChannelInterceptor;

@Configuration
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
}
