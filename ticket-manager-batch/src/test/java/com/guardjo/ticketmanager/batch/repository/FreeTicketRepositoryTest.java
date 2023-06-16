package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.config.JpaConfig;
import com.guardjo.ticketmanager.batch.domain.FreeTicket;
import com.guardjo.ticketmanager.batch.domain.FreeTicketStatus;
import com.guardjo.ticketmanager.batch.domain.MemberGroup;
import com.guardjo.ticketmanager.batch.domain.Ticket;
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
class FreeTicketRepositoryTest {
    @Autowired
    private FreeTicketRepository freeTicketRepository;

    private final static long TEST_DATA_SIZE = 1L;

    @DisplayName("신규 FreeTicket 저장 테스트")
    @Test
    void testSaveFreeTicket() {
        FreeTicket expected= FreeTicket.builder()
                .status(FreeTicketStatus.MOT_RECEIVE)
                .memberGroup(MemberGroup.builder().id(1L).build())
                .ticket(Ticket.builder().id(1L).build())
                .build();

        FreeTicket actual = freeTicketRepository.save(expected);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("특정 FreeTicket 조회 테스트")
    @Test
    void testFindFreeTicket() {
        long freeTicketId = 1L;

        // data.sql 참고
        long groupId = 1L;
        FreeTicketStatus freeTicketStatus = FreeTicketStatus.MOT_RECEIVE;

        FreeTicket freeTicket = freeTicketRepository.findById(freeTicketId).orElseThrow();

        assertThat(freeTicket)
                .hasFieldOrPropertyWithValue("status", freeTicketStatus)
                .extracting("memberGroup")
                .hasFieldOrPropertyWithValue("id", groupId);
    }

    @DisplayName("전체 FreeTicket 조회 테스트")
    @Test
    void testFindAllFreeTickets() {
        List<FreeTicket> freeTicketList = freeTicketRepository.findAll();

        assertThat(freeTicketList.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("특정 FreeTicket 제거 테스트")
    @Test
    void testDeleteFreeTicket() {
        long freeTicketId = 1L;

        freeTicketRepository.deleteById(freeTicketId);

        assertThat(freeTicketRepository.count()).isEqualTo(TEST_DATA_SIZE - 1);
    }
}