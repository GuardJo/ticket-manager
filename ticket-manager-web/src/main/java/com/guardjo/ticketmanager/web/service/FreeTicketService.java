package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.FreeTicketViewData;
import io.github.guardjo.ticketmanager.common.domain.FreeTicket;
import io.github.guardjo.ticketmanager.common.domain.MemberGroup;
import io.github.guardjo.ticketmanager.common.domain.Program;
import io.github.guardjo.ticketmanager.common.domain.Ticket;
import io.github.guardjo.ticketmanager.common.repository.FreeTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FreeTicketService {
    private final FreeTicketRepository freeTicketRepository;

    /**
     * 현재까지 생성된 무료 이용권 발급 현황을 반환한다.
     *
     * @return FreeTicketViewData 목록
     */
    @Transactional(readOnly = true)
    public List<FreeTicketViewData> findFreeTicketViewDataList() {
        log.info("Find All FreeTicketViewData");
        return freeTicketRepository.findAll().stream()
                .map(FreeTicketViewData::from)
                .collect(Collectors.toList());
    }

    /**
     * 신규 무료 이용권 발급 정보를 저장한다.
     *
     * @param program     무료 이용권으로 발급할 프로그램
     * @param memberGroup 무료 이용권 발급 대상 사용자 그룹
     */
    public void saveNewFreeTickets(Program program, MemberGroup memberGroup) {
        FreeTicket newFreeTicket = freeTicketRepository.save(
                FreeTicket.builder()
                        .program(program)
                        .memberGroup(memberGroup)
                        .build()
        );

        log.info("Save New FreeTicket, freeTicketId = {}", newFreeTicket.getId());
    }
}
