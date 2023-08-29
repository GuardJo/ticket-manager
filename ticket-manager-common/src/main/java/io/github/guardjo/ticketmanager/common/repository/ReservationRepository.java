package io.github.guardjo.ticketmanager.common.repository;

import io.github.guardjo.ticketmanager.common.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByStartedTimeGreaterThanEqualAndFinishedTimeLessThanEqual(LocalDateTime from, LocalDateTime to);

    @Query(value = "select r from Reservation r where r.createdTime between :from and :to")
    List<Reservation> findAllByTodayNewReservations(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
