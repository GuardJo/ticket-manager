package io.github.guardjo.ticketmanager.batch.model.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.guardjo.ticketmanager.batch.domain.Notification;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public record KakaoMessageTemplate(
    String objectType,
    String title,
    String text,
    KakaoMessageLink link,
    String buttonTitle
) {
    public static KakaoMessageTemplate of(String text, KakaoMessageLink link) {
        return new KakaoMessageTemplate("text", "예약 알림", text, link, "예약 확인");
    }

    public static KakaoMessageTemplate from(Notification notification) {
        // TODO 향후 다른 사이트 경로로 바꿀 것
        KakaoMessageLink kakaoMessageLink = KakaoMessageLink.of(
                "https://developers.kakao.com",
                "https://developers.kakao.com"
        );
        
        return KakaoMessageTemplate.of(notification.getContent(), kakaoMessageLink);
    }
}
