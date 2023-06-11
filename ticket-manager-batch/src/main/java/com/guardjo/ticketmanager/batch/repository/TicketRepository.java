package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
