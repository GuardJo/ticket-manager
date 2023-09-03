package com.guardjo.ticketmanager.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/reservation")
@Controller
@Slf4j
public class FreeTicketController {
    @GetMapping
    public String getFreeTickets() {
        return "/tickets/reservation";
    }
}
