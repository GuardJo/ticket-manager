package io.github.guardjo.ticketmanager.common.repository;

import io.github.guardjo.ticketmanager.common.config.JpaConfig;
import io.github.guardjo.ticketmanager.common.domain.Member;
import io.github.guardjo.ticketmanager.common.domain.Program;
import io.github.guardjo.ticketmanager.common.domain.Ticket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@Import(JpaConfig.class)
@DataJpaTest
class TicketRepositoryTest {
    @Autowired
    private TicketRepository ticketRepository;

    private final static long TEST_DATA_SIZE = 4L;

    @DisplayName("신규 Ticket 저장 테스트")
    @Test
    void testCreateTicket() {
        Member member = Member.builder().id(1L).build();
        Program program = Program.builder().id(1L).build();

        Ticket ticket = Ticket.builder()
                .remainingCount(10)
                .startedTime(LocalDateTime.now())
                .expiredTime(LocalDateTime.MAX)
                .member(member)
                .program(program)
                .build();

        Ticket actual = ticketRepository.save(ticket);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("remainingCount", ticket.getRemainingCount())
                .hasFieldOrPropertyWithValue("startedTime", ticket.getStartedTime())
                .hasFieldOrPropertyWithValue("expiredTime", ticket.getExpiredTime());
    }

    @DisplayName("특정 Ticket 객체 조회 테스트")
    @Test
    void testFindTicket() {
        // data.sql 참고
        String memberName = "tester";
        String programName = "PT 10회권";
        int remainingCount = 10;

        Ticket actual = ticketRepository.findById(1L).orElseThrow();

        assertThat(actual.getRemainingCount()).isEqualTo(remainingCount);
        assertThat(actual.getMember().getName()).isEqualTo(memberName);
        assertThat(actual.getProgram().getName()).isEqualTo(programName);
    }

    @DisplayName("전체 Ticket 객체 조회 테스트")
    @Test
    void testFindAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();

        assertThat(tickets.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("특정 Ticket 객체 수정 테스트")
    @Test
    void testUpdateTicket() {
        long ticketId = 1L;
        int remainingCount = 1;

        Ticket oldTicket = ticketRepository.findById(ticketId).orElseThrow();
        oldTicket.setRemainingCount(remainingCount);
        ticketRepository.flush();

        Ticket updateTicket = ticketRepository.findById(ticketId).orElseThrow();

        assertThat(updateTicket.getRemainingCount()).isEqualTo(remainingCount);
    }

    @DisplayName("Ticket 객체 제거 테스트")
    @Test
    void testDeleteTicket() {
        ticketRepository.deleteById(1L);

        assertThat(ticketRepository.count()).isEqualTo(TEST_DATA_SIZE - 1);
    }
}