package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.config.JpaConfig;
import com.guardjo.ticketmanager.batch.domain.Notification;
import com.guardjo.ticketmanager.batch.domain.NotificationStatus;
import com.guardjo.ticketmanager.batch.domain.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@Import(JpaConfig.class)
@DataJpaTest
class NotificationRepositoryTest {
    @Autowired
    private NotificationRepository notificationRepository;

    private final static long TEST_DATA_SIZE = 2L;

    @DisplayName("신규 Notification 저장 테스트")
    @Test
    void testCreateNotification() {
        Notification newNotification = Notification.builder()
                .status(NotificationStatus.NOT_SEND)
                .cootent("알림 테스트")
                .kakaoUUID("test uuid")
                .reservation(Reservation.builder().id(1L).build())
                .build();

        Notification savedNotification = notificationRepository.save(newNotification);

        assertThat(savedNotification).isEqualTo(newNotification);
    }

    @DisplayName("특정 Notification 조회 테스트")
    @Test
    void testReadNotification() {
        long notificationnId = 1L;
        // data.sql 참고
        String expectedContent = "Test Notification1";

        Notification actual = notificationRepository.findById(notificationnId).orElseThrow();

        assertThat(actual).isNotNull();
        assertThat(actual.getCootent()).isEqualTo(expectedContent);
    }

    @DisplayName("전체 Notification 목록 조회 테스트")
    @Test
    void testRedAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();

        assertThat(notifications.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("특정 Notification 수정 테스트")
    @Test
    void testUpdateNotification() {
        long notificationId = 1L;

        Notification oldNotification = notificationRepository.findById(notificationId).orElseThrow();

        oldNotification.setStatus(NotificationStatus.SENT);

        notificationRepository.flush();

        Notification newNotification = notificationRepository.findById(notificationId).orElseThrow();

        assertThat(newNotification.getStatus()).isEqualTo(NotificationStatus.SENT);
    }

    @DisplayName("특정 Notification 삭제 테스트")
    @Test
    void testDeleteNotification() {
        long notificationId = 1L;

        notificationRepository.deleteById(notificationId);

        assertThat(notificationRepository.count()).isEqualTo(TEST_DATA_SIZE - 1);
    }
}