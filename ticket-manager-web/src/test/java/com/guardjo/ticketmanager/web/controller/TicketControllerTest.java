package com.guardjo.ticketmanager.web.controller;

import com.guardjo.ticketmanager.web.data.TicketViewData;
import com.guardjo.ticketmanager.web.service.ReservationService;
import com.guardjo.ticketmanager.web.util.TestDataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TicketController.class)
class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @DisplayName("예약 현황 조회 페이지 반환 테스트")
    @Test
    void testListPage() throws Exception {
        List<TicketViewData> ticketViewDataList = List.of(TestDataGenerator.ticketViewData(1L));

        given(reservationService.findReservationTickets()).willReturn(ticketViewDataList);

        mockMvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("tickets"))
                .andExpect(view().name("/tickets/list"));

        then(reservationService).should().findReservationTickets();
    }
}