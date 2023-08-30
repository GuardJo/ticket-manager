package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.TicketViewData;
import com.guardjo.ticketmanager.web.util.TestDataGenerator;
import io.github.guardjo.ticketmanager.common.domain.Reservation;
import io.github.guardjo.ticketmanager.common.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationService reservationService;

    @Test
    void testFindAllTickets() {
        List<Reservation> reservations = List.of(TestDataGenerator.reservation());
        List<TicketViewData> expected = reservations.stream()
                .map(TicketViewData::from)
                .collect(Collectors.toList());

        given(reservationRepository.findAll()).willReturn(reservations);

        List<TicketViewData> actual = reservationService.findReservationTickets();

        assertThat(actual).isEqualTo(expected);

        then(reservationRepository).should().findAll();
    }
}