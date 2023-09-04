package com.guardjo.ticketmanager.web.controller;

import com.guardjo.ticketmanager.web.data.FreeTicketViewData;
import com.guardjo.ticketmanager.web.data.TicketSimpleData;
import com.guardjo.ticketmanager.web.data.UserGroupSimpleData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/free-tickets")
@Controller
@Slf4j
public class FreeTicketController {
    @GetMapping
    public String getFreeTickets(ModelMap modelMap) {
        List<FreeTicketViewData> freeTicketViewDataList = List.of(
                FreeTicketViewData.create(1, "test", "test", "test", LocalDateTime.now()),
                FreeTicketViewData.create(2, "test2", "Test2", "test", LocalDateTime.now())
        );

        List<TicketSimpleData> ticketSimpleDataList = List.of(
                TicketSimpleData.create(1, "testTicket1"),
                TicketSimpleData.create(2, "testTicket2")
        );

        List<UserGroupSimpleData> userGroupSimpleDataList = List.of(
                UserGroupSimpleData.create(1, "group1"),
                UserGroupSimpleData.create(2, "group2")
        );

        modelMap.addAttribute("freeTickets", freeTicketViewDataList);
        modelMap.addAttribute("tickets", ticketSimpleDataList);
        modelMap.addAttribute("userGroups", userGroupSimpleDataList);

        return "/tickets/free-tickets";
    }
}
