package io.github.guardjo.ticketmanager.common.repository;

import io.github.guardjo.ticketmanager.common.domain.FreeTicket;
import io.github.guardjo.ticketmanager.common.domain.FreeTicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreeTicketRepository extends JpaRepository<FreeTicket, Long> {
    List<FreeTicket> findByStatus(FreeTicketStatus freeTicketStatus);
}
