package io.github.guardjo.ticketmanager.batch.repository;

import io.github.guardjo.ticketmanager.batch.domain.FreeTicket;
import io.github.guardjo.ticketmanager.batch.domain.FreeTicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeTicketRepository extends JpaRepository<FreeTicket, Long> {
    List<FreeTicket> findByStatus(FreeTicketStatus freeTicketStatus);
}
