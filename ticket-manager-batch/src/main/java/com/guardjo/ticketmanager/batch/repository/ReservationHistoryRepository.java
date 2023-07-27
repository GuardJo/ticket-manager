package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.domain.ReservationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationHistoryRepository extends JpaRepository<ReservationHistory, Long> {
}
