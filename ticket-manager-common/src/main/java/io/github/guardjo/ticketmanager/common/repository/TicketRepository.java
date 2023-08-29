package io.github.guardjo.ticketmanager.common.repository;

import io.github.guardjo.ticketmanager.common.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
