package io.github.guardjo.ticketmanager.batch.job.processor;

import io.github.guardjo.ticketmanager.batch.config.KakaoApiProperty;
import io.github.guardjo.ticketmanager.batch.config.KakaoConfig;
import io.github.guardjo.ticketmanager.batch.domain.Notification;
import io.github.guardjo.ticketmanager.batch.domain.NotificationStatus;
import io.github.guardjo.ticketmanager.batch.model.constant.KakaoApiConstants;
import io.github.guardjo.ticketmanager.batch.model.kakao.KakaoMessageSendResponse;
import io.github.guardjo.ticketmanager.batch.util.TestDataGenerator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestBodyUriSpec;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import static org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

@ActiveProfiles("test")
class NotificationSendProcessorTest {

    @DisplayName("알림 전송 프로세스 테스트 (mock)")
    @ExtendWith(MockitoExtension.class)
    @ConfigurationPropertiesScan(basePackageClasses = KakaoApiProperty.class)
    @Nested
    class MockWebClientTest {
        @Mock
        private WebClient webClient;

        @InjectMocks
        private NotificationSendProcessor notificationSendProcessor;

        private final static Notification TEST_NOTIFICATION = TestDataGenerator.notification(TestDataGenerator.reservation());
        private final static ResponseSpec TEST_RESPONSE = mock(ResponseSpec.class);

        @BeforeEach
        void setUp() {
            RequestBodyUriSpec requestBodyUriSpec = mock(RequestBodyUriSpec.class);
            RequestBodySpec requestBodySpec = mock(RequestBodySpec.class);
            RequestHeadersSpec requestHeadersSpec = mock(RequestHeadersSpec.class);

            given(webClient.post()).willReturn(requestBodyUriSpec);
            given(requestBodyUriSpec.uri(KakaoApiConstants.MESSAGE_SEND_URL)).willReturn(requestBodySpec);
            given(requestBodySpec.contentType(MediaType.APPLICATION_FORM_URLENCODED)).willReturn(requestBodySpec);
            given(requestBodySpec.body(any())).willReturn(requestHeadersSpec);
            given(requestHeadersSpec.retrieve()).willReturn(TEST_RESPONSE);
        }

        @AfterEach
        void tearDown() {
            then(webClient).should().post();
        }

        @DisplayName("알림 전송 Process 테스트")
        @ParameterizedTest
        @MethodSource("testArguments")
        void testProcess(int resultCode, NotificationStatus status) throws Exception {
            KakaoMessageSendResponse expected = KakaoMessageSendResponse.of(resultCode);
            Mono<KakaoMessageSendResponse> mono = mock(Mono.class);

            given(TEST_RESPONSE.bodyToMono(KakaoMessageSendResponse.class)).willReturn(mono);
            given(mono.block()).willReturn(expected);

            Notification actual = notificationSendProcessor.process(TEST_NOTIFICATION);

            then(mono).should().block();

            assertThat(actual.getStatus()).isEqualTo(status);
        }

        public static Stream<Arguments> testArguments() {
            return Stream.of(
                    Arguments.arguments(0, NotificationStatus.SENT),
                    Arguments.arguments(-1, NotificationStatus.SEND_ERROR)
            );
        }
    }

    @Disabled
    @DisplayName("실제 외부 API 호출 테스트")
    @SpringBootTest(classes = NotificationSendProcessor.class)
    @AutoConfigureWebClient
    @ConfigurationPropertiesScan(basePackageClasses = KakaoApiProperty.class)
    @ContextConfiguration(classes = KakaoConfig.class)
    @Nested
    class RealWebClientTest {
        @Autowired
        private NotificationSendProcessor notificationSendProcessor;

        @DisplayName("알림 전송 Process 테스트")
        @Test
        void testProcess() throws Exception {
            Notification expected = TestDataGenerator.notification(TestDataGenerator.reservation());

            Notification actual = notificationSendProcessor.process(expected);

            assertThat(actual.getContent()).isEqualTo(expected.getContent());
            assertThat(actual.getStatus()).isEqualTo(NotificationStatus.SENT);
        }
    }
}