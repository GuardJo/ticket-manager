package com.guardjo.ticketmanager.web.controller;

import com.guardjo.ticketmanager.web.data.FreeTicketViewData;
import com.guardjo.ticketmanager.web.data.TicketSimpleData;
import com.guardjo.ticketmanager.web.data.UserGroupSimpleData;
import com.guardjo.ticketmanager.web.service.FreeTicketService;
import com.guardjo.ticketmanager.web.service.MemberGroupService;
import com.guardjo.ticketmanager.web.service.TicketService;
import io.github.guardjo.ticketmanager.common.domain.MemberGroup;
import io.github.guardjo.ticketmanager.common.domain.Ticket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/free-tickets")
@Controller
@Slf4j
@RequiredArgsConstructor
public class FreeTicketController {
    private final FreeTicketService freeTicketService;
    private final TicketService ticketService;
    private final MemberGroupService memberGroupService;

    @GetMapping
    public String getFreeTickets(ModelMap modelMap) {
        List<FreeTicketViewData> freeTicketViewDataList = freeTicketService.findFreeTicketViewDataList();
        List<TicketSimpleData> ticketSimpleDataList = ticketService.findAllTicketSimpleDataList();
        List<UserGroupSimpleData> userGroupSimpleDataList = memberGroupService.findAllUserGroupSimpleDataList();

        modelMap.addAttribute("freeTickets", freeTicketViewDataList);
        modelMap.addAttribute("tickets", ticketSimpleDataList);
        modelMap.addAttribute("userGroups", userGroupSimpleDataList);

        return "/tickets/free-tickets";
    }

    @PostMapping
    public String saveNewFreeTickets(@RequestParam long ticketId, @RequestParam long userGroupId) {
        log.info("Request new FreeTickets, ticketId = {}, userGroupId = {}", ticketId, userGroupId);
        Ticket ticket = ticketService.findTicket(ticketId);
        MemberGroup memberGroup = memberGroupService.findMemberGroup(userGroupId);

        if (ticket == null || memberGroup == null) {
            log.warn("Not Found Ticket or MemberGroup");
        } else {
            freeTicketService.saveNewFreeTickets(ticket, memberGroup);
        }

        return "redirect:/free-tickets";
    }
}
