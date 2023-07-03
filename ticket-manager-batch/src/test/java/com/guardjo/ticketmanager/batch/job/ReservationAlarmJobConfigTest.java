package com.guardjo.ticketmanager.batch.job;

import com.guardjo.ticketmanager.batch.config.KakaoApiProperty;
import com.guardjo.ticketmanager.batch.config.KakaoConfig;
import com.guardjo.ticketmanager.batch.config.TestBatchConfig;
import com.guardjo.ticketmanager.batch.config.TestWebClientConfig;
import com.guardjo.ticketmanager.batch.domain.Member;
import com.guardjo.ticketmanager.batch.domain.Reservation;
import com.guardjo.ticketmanager.batch.domain.Ticket;
import com.guardjo.ticketmanager.batch.job.processor.NotificationSendProcessor;
import com.guardjo.ticketmanager.batch.repository.NotificationRepository;
import com.guardjo.ticketmanager.batch.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest
@ContextConfiguration(classes = {
        ReservationAlarmJobConfig.class,
        TestBatchConfig.class,
        NotificationSendProcessor.class,
        TestWebClientConfig.class
})
class ReservationAlarmJobConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    private final static int TEST_SIZE = 4;
    private final static String TEST_JOB_NAME = "notificationCreateJob";

    @BeforeEach
    void setUp() {
        System.out.println("Test Data Initializing");

        notificationRepository.deleteAll();
        notificationRepository.flush();

        LocalDateTime afterTenMinutes = LocalDateTime.now().plusMinutes(10);

        for (int i = 0; i < TEST_SIZE; i++) {
            Reservation reservation = Reservation.builder()
                    .usedCount(0)
                    .startedTime(afterTenMinutes)
                    .finishedTime(LocalDateTime.MAX)
                    .member(Member.builder().id(i + 1L).build())
                    .ticket(Ticket.builder().id(i + 1L).build())
                    .build();

            reservationRepository.save(reservation);
        }

        reservationRepository.flush();

        System.out.println("Test Data Initialized");
    }

    @DisplayName("신규 알림 생성 Job 테스트")
    @Test
    void testCreateNotificationJob() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();

        long notificationSize = notificationRepository.count();

        assertThat(jobInstance.getJobName()).isEqualTo(TEST_JOB_NAME);
        assertThat(notificationSize).isEqualTo(TEST_SIZE);
    }
}