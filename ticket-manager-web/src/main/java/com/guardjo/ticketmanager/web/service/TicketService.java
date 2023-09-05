package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.TicketSimpleData;
import io.github.guardjo.ticketmanager.common.domain.Ticket;
import io.github.guardjo.ticketmanager.common.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    /**
     * 현재 기준 저장된 전체 이용권 목록을 반환한다.
     *
     * @return TicketSimpleData 목록
     */
    @Transactional(readOnly = true)
    public List<TicketSimpleData> findAllTicketSimpleDataList() {
        log.info("Find All TicketSimpleData List");
        
        return ticketRepository.findAll().stream()
                .map(TicketSimpleData::from)
                .toList();
    }

    /**
     * 주어진 식별자에 대한 이용권 데이터를 반환한다.
     *
     * @param ticketId 이용권 식별키
     * @return Ticket Entity
     */
    @Transactional(readOnly = true)
    public Ticket findTicket(long ticketId) {
        log.info("Find Ticket Entity, ticketId = {}", ticketId);

        return ticketRepository.findById(ticketId).orElse(null);
    }
}
