package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.config.JpaConfig;
import com.guardjo.ticketmanager.batch.domain.ReservationHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@Import(JpaConfig.class)
@DataJpaTest
class ReservationHistoryRepositoryTest {
    @Autowired
    private ReservationHistoryRepository reservationHistoryRepository;

    private final static long TEST_DATA_SIZE= 7L;

    @DisplayName("신규 ReservationHistory 저장 테스트")
    @Test
    void testCreateReservationHistory() {
        LocalDate current = LocalDate.now();
        ReservationHistory newRevisionHisotry = ReservationHistory.builder()
                .historyDate(current)
                .totalNewReservationCount(10)
                .totalReservationUsedCount(10)
                .build();

        ReservationHistory actual = reservationHistoryRepository.save(newRevisionHisotry);

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("totalNewReservationCount", 10)
                .hasFieldOrPropertyWithValue("totalReservationUsedCount", 10)
                .hasFieldOrPropertyWithValue("historyDate", current);
    }

    @DisplayName("ReservationHistory 단일 조회 테스트")
    @Test
    void testFindReservationHistory() {
        long id = 1L;
        int expectedNewCount = 1;
        int expectedUsedCount = 1;
        LocalDate expectedDate = LocalDate.parse("2023-04-24", DateTimeFormatter.ISO_LOCAL_DATE);

        ReservationHistory actual = reservationHistoryRepository.findById(id).orElseThrow();

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("totalNewReservationCount", expectedNewCount)
                .hasFieldOrPropertyWithValue("totalReservationUsedCount", expectedUsedCount)
                .hasFieldOrPropertyWithValue("historyDate", expectedDate);
    }

    @DisplayName("특정 historyDate에 해당하는 ReservationHistory 존재 여부 반환 테스트")
    @ParameterizedTest
    @MethodSource("initDataOfHistoryDate")
    void testExistsHistoryDate(String date, boolean expected) {
        LocalDate today = LocalDate.parse(date);

        boolean actual = reservationHistoryRepository.existsByHistoryDate(today);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("특정 날짜의 ReservationHistory 조회 테스트")
    @Test
    void testFindByHistoryDate() {
        LocalDate date = LocalDate.parse("2023-04-24");

        ReservationHistory actual = reservationHistoryRepository.findByHistoryDate(date)
                .orElseThrow();

        assertThat(actual.getHistoryDate()).isEqualTo(date);
    }

    @DisplayName("특정 날짜를 기준으로 이전 1주일 간의 ReservationHistoryData 조회 테스트")
    @Test
    void testFindWeeklyHistories() {
        LocalDate to = LocalDate.parse("2023-04-30");
        LocalDate from = to.minusWeeks(1L);

        List<ReservationHistory> reservationHistories = reservationHistoryRepository.findWeeklyData(from, to);

        assertThat(reservationHistories.size()).isEqualTo(TEST_DATA_SIZE);
    }

    @DisplayName("ReservationHistory 전체 목록 조회 테스트")
    @Test
    void testFindAllReservationHistories() {
        List<ReservationHistory> reservationHistories = reservationHistoryRepository.findAll();

        assertThat(reservationHistories.size()).isEqualTo(TEST_DATA_SIZE);
    }
    
    @DisplayName("특정 RevisionHistory 갱신 테스트")
    @Test
    void testUpdateReservationHistory() {
        long id = 1L;
        int updateCount = 1;
        ReservationHistory oldData = reservationHistoryRepository.findById(id).orElseThrow();;
        oldData.setTotalNewReservationCount(updateCount);
        reservationHistoryRepository.flush();

        ReservationHistory updateData = reservationHistoryRepository.findById(id).orElseThrow();

        assertThat(updateData.getTotalNewReservationCount()).isEqualTo(updateCount);
    }

    @DisplayName("특정 RevisionHistory 삭제 테스트")
    @Test
    void testDeleteReservationHistory() {
        reservationHistoryRepository.deleteById(1L);

        assertThat(reservationHistoryRepository.count()).isEqualTo(TEST_DATA_SIZE - 1);
    }

    static Stream<Arguments> initDataOfHistoryDate() {
        return Stream.of(
                Arguments.arguments("2023-04-24", true),
                Arguments.arguments("2020-02-02", false)
        );
    }
}