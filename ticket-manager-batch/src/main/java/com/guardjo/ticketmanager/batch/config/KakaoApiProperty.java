package com.guardjo.ticketmanager.batch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao-api")
public record KakaoApiProperty(
        String host,
        String accessToken
) {
}
