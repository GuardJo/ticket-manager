package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
