package io.github.guardjo.ticketmanager.batch.job.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.guardjo.ticketmanager.batch.domain.Notification;
import io.github.guardjo.ticketmanager.batch.domain.NotificationStatus;
import io.github.guardjo.ticketmanager.batch.model.constant.KakaoApiConstants;
import io.github.guardjo.ticketmanager.batch.model.kakao.KakaoMessageSendResponse;
import io.github.guardjo.ticketmanager.batch.model.kakao.KakaoMessageTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationSendProcessor implements ItemProcessor<Notification, Notification> {
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Notification process(Notification item) throws Exception {
        log.info("Sending Kakao notification messages");

        KakaoMessageTemplate messageTemplate = KakaoMessageTemplate.from(item);
        String request = objectMapper.writeValueAsString(messageTemplate);

        KakaoMessageSendResponse messageSendResponse = webClient.post()
                .uri(KakaoApiConstants.MESSAGE_SEND_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(KakaoApiConstants.MESSAGE_SEND_PARAMETER_NAME, request))
                .retrieve()
                .bodyToMono(KakaoMessageSendResponse.class)
                .block();

        if (messageSendResponse.resultCode() != 0) {
            log.warn("Failed Sending message, resultCode = {}", messageSendResponse.resultCode());
            item.setStatus(NotificationStatus.SEND_ERROR);
        } else {
            log.info("Successed Sended messages");
            item.setStatus(NotificationStatus.SENT);
        }

        return item;
    }
}
