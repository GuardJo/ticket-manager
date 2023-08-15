package com.guardjo.ticketmanager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    @GetMapping
    public String tickets() {
        return "/tickets/list";
    }
}
