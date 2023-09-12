package io.github.guardjo.ticketmanager.common.repository;

import io.github.guardjo.ticketmanager.common.domain.ReservationHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory, Long> {
    boolean existsByHistoryDate(LocalDate today);

    Optional<ReservationHistory> findByHistoryDate(LocalDate date);

    @Query(
            "select rh from ReservationHistory rh where rh.historyDate between :from and :to"
    )
    List<ReservationHistory> findWeeklyData(@Param("from") LocalDate from, @Param("to") LocalDate to);

    @Query(
            "select rh from ReservationHistory rh order by rh.historyDate desc"
    )
    List<ReservationHistory> findRecentlyData(Pageable pageable);
}
