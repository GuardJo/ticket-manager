package com.guardjo.ticketmanager.batch.job;

import com.guardjo.ticketmanager.batch.config.TestBatchConfig;
import com.guardjo.ticketmanager.batch.domain.Reservation;
import com.guardjo.ticketmanager.batch.domain.Ticket;
import com.guardjo.ticketmanager.batch.repository.ReservationRepository;
import com.guardjo.ticketmanager.batch.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest
@ContextConfiguration(classes = {TestBatchConfig.class, FinishReservationJobConfig.class})
class FinishReservationJobConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    private final static String JOB_NAME = "finishedReservationJob";
    private final static long TEST_DATA_SIZE = 4L; // TEST DB 내 Reservation 수
    private final static long TICKET_UPDATE_SIZE = 3L; // TEST DB 중 ticket의 remaining_count가 10개인 데이터 수

    @DisplayName("티켓 및 예약 차감 Job 테스트")
    @Test
    void testExecuteJob() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();

        List<Reservation> reservations = reservationRepository.findAll();
        long usedReservationCount = reservations.stream()
                .filter(reservation -> reservation.getUsedCount() > 0)
                .count();

        List<Ticket> tickets = ticketRepository.findAll();
        long usedTicketCount = tickets.stream()
                .filter(ticket -> ticket.getRemainingCount() < 10)
                .count();

        assertThat(jobInstance.getJobName()).isEqualTo(JOB_NAME);
        assertThat(usedReservationCount).isEqualTo(TEST_DATA_SIZE);
        assertThat(usedTicketCount).isEqualTo(TICKET_UPDATE_SIZE);
    }
}