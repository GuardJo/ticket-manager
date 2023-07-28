package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.config.JpaConfig;
import com.guardjo.ticketmanager.batch.domain.Member;
import com.guardjo.ticketmanager.batch.domain.Reservation;
import com.guardjo.ticketmanager.batch.domain.Ticket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@Import(JpaConfig.class)
@DataJpaTest
class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    private final static long TEST_DATA_SIZE = 6L;

    @DisplayName("신규 Reservation 저장 테스트")
    @Test
    void testCreateReservation() {
        Member member = Member.builder().id(1L).build();
        Ticket ticket = Ticket.builder().id(1L).build();

        Reservation newReservation = Reservation
                .builder()
                .usedCount(0)
                .startedTime(LocalDateTime.now())
                .finishedTime(LocalDateTime.MAX)
                .member(member)
                .ticket(ticket)
                .build();

        Reservation actual = reservationRepository.save(newReservation);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("usedCount", newReservation.getUsedCount())
                .hasFieldOrPropertyWithValue("startedTime", newReservation.getStartedTime())
                .hasFieldOrPropertyWithValue("finishedTime", newReservation.getFinishedTime());
    }

    @DisplayName("특정 Reservation 객체 조회 테스트")
    @Test
    void testFindReservation() {
        // data.sql 참고
        String prgramName = "PT 10회권";
        String memberName = "tester";
        int usedCount = 0;

        Reservation reservation = reservationRepository.findById(1L).orElseThrow();

        assertThat(reservation.getUsedCount()).isEqualTo(usedCount);
        assertThat(reservation.getTicket().getProgram().getName()).isEqualTo(prgramName);
        assertThat(reservation.getMember().getName()).isEqualTo(memberName);
    }

    @DisplayName("특정 기간대의 Revision 객체 목록 조회 테스트")
    @Test
    void testFindAllReservationStartDateBetweenFinishDate() {
        LocalDateTime finishDate = LocalDate.parse("2023-05-03", DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
        LocalDateTime startDate = finishDate.minusDays(1L);
        long expectedCount = 2L; // data.sql 내 데이터 기반

        List<Reservation> actual = reservationRepository
                .findAllByStartedTimeGreaterThanEqualAndFinishedTimeLessThanEqual(startDate, finishDate);
        
        assertThat(actual.size()).isEqualTo(expectedCount);
    }

    @DisplayName("전체 Reservation 객체 조회 테스트")
    @Test
    void testFindAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("특정 Reservation 객체 수정 테스트")
    @Test
    void testUpdateReservation() {
        long reservationId = 1L;
        int updateUsedCount = 10;

        Reservation oldReservation = reservationRepository.findById(reservationId).orElseThrow();
        oldReservation.setUsedCount(updateUsedCount);
        reservationRepository.flush();

        Reservation updateReservation = reservationRepository.findById(reservationId).orElseThrow();

        assertThat(updateReservation.getUsedCount()).isEqualTo(updateUsedCount);
    }

    @DisplayName("특정 Reservation 객체 삭제 테스트")
    @Test
    void testDeleteReservation() {
        reservationRepository.deleteById(1L);

        assertThat(reservationRepository.count()).isEqualTo(TEST_DATA_SIZE - 1);
    }
}