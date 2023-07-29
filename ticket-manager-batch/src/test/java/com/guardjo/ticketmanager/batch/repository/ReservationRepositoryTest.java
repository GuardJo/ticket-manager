package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.config.JpaConfig;
import com.guardjo.ticketmanager.batch.config.TestJpaConfig;
import com.guardjo.ticketmanager.batch.domain.Member;
import com.guardjo.ticketmanager.batch.domain.Reservation;
import com.guardjo.ticketmanager.batch.domain.Ticket;
import com.guardjo.ticketmanager.batch.util.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
        Reservation expected = createNewReservation();

        Reservation actual = reservationRepository.save(expected);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("usedCount", expected.getUsedCount())
                .hasFieldOrPropertyWithValue("startedTime", expected.getStartedTime())
                .hasFieldOrPropertyWithValue("finishedTime", expected.getFinishedTime());
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

    @DisplayName("지정된 날짜에 생성된 Reservation 목록 조회 테스트")
    @ParameterizedTest
    @MethodSource("initTodayNewReservations")
    void testFindAllTodayNewReservations(List<Reservation> reservations) {
        LocalDate today = LocalDate.now();
        LocalDate next = today.plusDays(1L);
        LocalDateTime from = today.atStartOfDay();
        LocalDateTime to = next.atStartOfDay();
        int expectedSize = reservations.size();

        reservationRepository.saveAll(reservations);
        reservationRepository.flush();

        List<Reservation> actual = reservationRepository.findAllByTodayNewReservations(from, to);

        assertThat(actual.size()).isEqualTo(expectedSize);
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

    static Stream<Arguments> initTodayNewReservations() {
        List<Reservation> reservations = List.of(
                createNewReservation(),
                createNewReservation()
        );

        return Stream.of(
                Arguments.arguments(reservations)
        );
    }

    private static Reservation createNewReservation() {
        Member member = Member.builder().id(1L).build();
        Ticket ticket = Ticket.builder().id(1L).build();

        return Reservation
                .builder()
                .usedCount(0)
                .startedTime(LocalDateTime.now())
                .finishedTime(LocalDateTime.MAX)
                .member(member)
                .ticket(ticket)
                .build();
    }
}