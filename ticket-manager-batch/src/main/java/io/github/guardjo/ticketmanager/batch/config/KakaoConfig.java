package io.github.guardjo.ticketmanager.batch.config;

import io.github.guardjo.ticketmanager.batch.model.constant.KakaoApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class KakaoConfig {
    private final KakaoApiProperty kakaoApiProperty;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(kakaoApiProperty.host())
                .defaultHeader(HttpHeaders.AUTHORIZATION,
                        KakaoApiConstants.AUTHORIZATION_PREFIX + " " + kakaoApiProperty.accessToken())
                .build();
    }
}
