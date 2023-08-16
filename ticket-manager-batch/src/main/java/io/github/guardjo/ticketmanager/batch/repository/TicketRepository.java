package io.github.guardjo.ticketmanager.batch.repository;

import io.github.guardjo.ticketmanager.batch.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
