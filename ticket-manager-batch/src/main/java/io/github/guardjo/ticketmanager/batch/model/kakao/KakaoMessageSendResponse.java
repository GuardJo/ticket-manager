package io.github.guardjo.ticketmanager.batch.model.kakao;

public record KakaoMessageSendResponse(
        int resultCode
) {
    public static KakaoMessageSendResponse of(int resultCode) {
        return new KakaoMessageSendResponse(resultCode);
    }
}
