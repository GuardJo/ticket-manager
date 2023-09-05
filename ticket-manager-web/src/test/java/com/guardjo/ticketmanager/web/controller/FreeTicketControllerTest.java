package com.guardjo.ticketmanager.web.controller;

import com.guardjo.ticketmanager.web.service.FreeTicketService;
import com.guardjo.ticketmanager.web.service.MemberGroupService;
import com.guardjo.ticketmanager.web.service.TicketService;
import com.guardjo.ticketmanager.web.util.TestDataGenerator;
import io.github.guardjo.ticketmanager.common.domain.MemberGroup;
import io.github.guardjo.ticketmanager.common.domain.Ticket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FreeTicketController.class)
class FreeTicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FreeTicketService freeTicketService;
    @MockBean
    private TicketService ticketService;
    @MockBean
    private MemberGroupService memberGroupService;

    private final String FREE_TICKET_URL = "/free-tickets";

    @DisplayName("이용권 발급 페이지 반환 테스트")
    @Test
    void testGetFreeTickets() throws Exception {
        given(freeTicketService.findFreeTicketViewDataList()).willReturn(List.of());
        given(ticketService.findAllTicketSimpleDataList()).willReturn(List.of());
        given(memberGroupService.findAllUserGroupSimpleDataList()).willReturn(List.of());

        mockMvc.perform(get(FREE_TICKET_URL))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("freeTickets", "tickets", "userGroups"))
                .andExpect(view().name("/tickets/free-tickets"));

        then(freeTicketService).should().findFreeTicketViewDataList();
        then(ticketService).should().findAllTicketSimpleDataList();
        then(memberGroupService).should().findAllUserGroupSimpleDataList();
    }

    @DisplayName("신규 이용권 발급 요청 테스트")
    @ParameterizedTest
    @MethodSource("saveFreeTicketTestData")
    void testSaveNewFreeTickets(Ticket ticket, MemberGroup memberGroup) throws Exception {
        long ticketId = 1L;
        long groupId = 1L;

        given(ticketService.findTicket(eq(ticketId))).willReturn(ticket);
        given(memberGroupService.findMemberGroup(eq(groupId))).willReturn(memberGroup);
        if (ticket != null && memberGroup != null) {
            willDoNothing().given(freeTicketService).saveNewFreeTickets(eq(ticket), eq(memberGroup));
        }

        mockMvc.perform(post(FREE_TICKET_URL)
                        .queryParam("ticketId", String.valueOf(ticketId))
                        .queryParam("userGroupId", String.valueOf(groupId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(FREE_TICKET_URL));

        then(ticketService).should().findTicket(eq(ticketId));
        then(memberGroupService).should().findMemberGroup(eq(groupId));
        if (ticket != null && memberGroup != null) {
            then(freeTicketService).should().saveNewFreeTickets(eq(ticket), eq(memberGroup));
        }
    }

    static Stream<Arguments> saveFreeTicketTestData() {
        return Stream.of(
                Arguments.of(TestDataGenerator.ticket(), TestDataGenerator.memberGroup()),
                Arguments.of(TestDataGenerator.ticket(), null),
                Arguments.of(null, TestDataGenerator.memberGroup()),
                Arguments.of(null, null)
        );
    }
}