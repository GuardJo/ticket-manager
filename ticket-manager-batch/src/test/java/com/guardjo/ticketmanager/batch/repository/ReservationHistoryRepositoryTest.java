package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.config.JpaConfig;
import com.guardjo.ticketmanager.batch.domain.ReservationHistory;
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
class ReservationHistoryRepositoryTest {
    @Autowired
    private ReservationHistoryRepository reservationHistoryRepository;

    private final static long TEST_DATA_SIZE= 7L;

    @DisplayName("신규 ReservationHistory 저장 테스트")
    @Test
    void testCreateReservationHistory() {
        LocalDateTime current = LocalDateTime.now();
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
        LocalDateTime expectedDate = LocalDate.parse("2023-04-24", DateTimeFormatter.ISO_LOCAL_DATE)
                .atStartOfDay();

        ReservationHistory actual = reservationHistoryRepository.findById(id).orElseThrow();

        assertThat(actual)
                .isNotNull()
                .hasFieldOrPropertyWithValue("totalNewReservationCount", expectedNewCount)
                .hasFieldOrPropertyWithValue("totalReservationUsedCount", expectedUsedCount)
                .hasFieldOrPropertyWithValue("historyDate", expectedDate);
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
}