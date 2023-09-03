package com.guardjo.ticketmanager.batch.job;

import com.guardjo.ticketmanager.batch.config.TestBatchConfig;
import com.guardjo.ticketmanager.batch.domain.Member;
import com.guardjo.ticketmanager.batch.domain.Program;
import com.guardjo.ticketmanager.batch.domain.Ticket;
import com.guardjo.ticketmanager.batch.domain.TicketStatus;
import com.guardjo.ticketmanager.batch.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest
@ContextConfiguration(classes = {TestBatchConfig.class, ExpireTicketJobConfig.class})
class ExpireTicketJobConfigTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired
    private TicketRepository ticketRepository;

    private final static int TEST_DATA_SIZE = 10;
    private final static String EXPIRE_TICKET_JOB_NAME = "expireTicketJob";

    @BeforeEach
    void setUp() {
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < TEST_DATA_SIZE; i++) {
            Ticket ticket = Ticket.builder()
                    .startedTime(LocalDateTime.now().minusDays(1L))
                    .expiredTime(LocalDateTime.now().minusDays(1L))
                    .remainingCount(10)
                    .status(TicketStatus.PROGRESS)
                    .member(Member.builder().id(1L).build())
                    .program(Program.builder().id(1L).build())
                    .build();

            tickets.add(ticket);
        }

        ticketRepository.saveAll(tickets);
    }

    @DisplayName("이용권 만료 Job 실행 테스트")
    @Test
    void testExpireTicket() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobInstance jobInstance = jobExecution.getJobInstance();

        List<Ticket> tickets = ticketRepository.findAll();

        long expiredCount = tickets.stream()
                .filter((ticket) -> ticket.getStatus().equals(TicketStatus.EXPIRED))
                .count();

        assertThat(jobInstance.getJobName()).isEqualTo(EXPIRE_TICKET_JOB_NAME);
        assertThat(expiredCount).isEqualTo(TEST_DATA_SIZE);
    }
}