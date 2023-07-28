package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByStartedTimeGreaterThanEqualAndFinishedTimeLessThanEqual(LocalDateTime startDate, LocalDateTime finishDate);
}
