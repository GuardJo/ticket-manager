package com.guardjo.ticketmanager.web.controller;

import com.guardjo.ticketmanager.web.data.TicketViewData;
import com.guardjo.ticketmanager.web.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/tickets")
@Slf4j
@RequiredArgsConstructor
public class TicketController {
    private final ReservationService reservationService;

    @GetMapping
    public String tickets(ModelMap modelMap) {
        List<TicketViewData> tickets = reservationService.findReservationTickets();

        modelMap.addAttribute("tickets", tickets);

        log.info("Request Ticket-List view");

        return "tickets/list";
    }
}
