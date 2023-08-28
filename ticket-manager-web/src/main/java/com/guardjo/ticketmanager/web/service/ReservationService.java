package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.TicketViewData;
import io.github.guardjo.ticketmanager.batch.domain.Reservation;
import io.github.guardjo.ticketmanager.batch.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    /**
     * 예약된 이용권 목록을 반환한다.
     *
     * @return 예약된 이용권 목록
     */
    @Transactional(readOnly = true)
    public List<TicketViewData> findReservationTickets() {
        log.info("Find All ReservationTickets");

        List<Reservation> reservations = reservationRepository.findAll();

        return reservations.stream()
                .map(TicketViewData::from)
                .collect(Collectors.toList());
    }
}
