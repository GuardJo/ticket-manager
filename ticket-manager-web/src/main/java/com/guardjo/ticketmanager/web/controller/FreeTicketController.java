package com.guardjo.ticketmanager.web.controller;

import com.guardjo.ticketmanager.web.data.FreeTicketViewData;
import com.guardjo.ticketmanager.web.data.ProgramSimpleData;
import com.guardjo.ticketmanager.web.data.TicketSimpleData;
import com.guardjo.ticketmanager.web.data.UserGroupSimpleData;
import com.guardjo.ticketmanager.web.service.FreeTicketService;
import com.guardjo.ticketmanager.web.service.MemberGroupService;
import com.guardjo.ticketmanager.web.service.ProgramService;
import com.guardjo.ticketmanager.web.service.TicketService;
import io.github.guardjo.ticketmanager.common.domain.MemberGroup;
import io.github.guardjo.ticketmanager.common.domain.Program;
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
    private final ProgramService programService;
    private final MemberGroupService memberGroupService;

    @GetMapping
    public String getFreeTickets(ModelMap modelMap) {
        List<FreeTicketViewData> freeTicketViewDataList = freeTicketService.findFreeTicketViewDataList();
        List<ProgramSimpleData> programSimpleData = programService.getProgramSimpleDataList();
        List<UserGroupSimpleData> userGroupSimpleDataList = memberGroupService.findAllUserGroupSimpleDataList();

        modelMap.addAttribute("freeTickets", freeTicketViewDataList);
        modelMap.addAttribute("programs", programSimpleData);
        modelMap.addAttribute("userGroups", userGroupSimpleDataList);

        return "tickets/free-tickets";
    }

    @PostMapping
    public String saveNewFreeTickets(@RequestParam long programId, @RequestParam long userGroupId) {
        log.info("Request new FreeTickets, programId = {}, userGroupId = {}", programId, userGroupId);
        Program program = programService.getProgram(programId);
        MemberGroup memberGroup = memberGroupService.findMemberGroup(userGroupId);

        if (program == null || memberGroup == null) {
            log.warn("Not Found Ticket or MemberGroup");
        } else {
            freeTicketService.saveNewFreeTickets(program, memberGroup);
        }

        return "redirect:/free-tickets";
    }
}
