package com.base.demo.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 클래스, 인터페이스, 열거형, 애너테이션에 사용
@Retention(RetentionPolicy.RUNTIME)  // 런타임까지 유지
public @interface TopicName {
    String value();
}
