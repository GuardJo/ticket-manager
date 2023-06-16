package com.guardjo.ticketmanager.batch.repository;

import com.guardjo.ticketmanager.batch.domain.FreeTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreeTicketRepository extends JpaRepository<FreeTicket, Long> {
}
