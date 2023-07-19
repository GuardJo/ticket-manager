package com.guardjo.ticketmanager.batch.model.kakao;

public record KakaoMessageLink(
        String webUrl,
        String mobileWebUrl
) {
    public static KakaoMessageLink of(String webUrl, String mobileWebUrl) {
        return new KakaoMessageLink(webUrl, mobileWebUrl);
    }
}
