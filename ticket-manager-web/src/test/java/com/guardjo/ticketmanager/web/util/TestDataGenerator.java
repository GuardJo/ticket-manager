package com.guardjo.ticketmanager.web.util;

import com.guardjo.ticketmanager.web.data.TicketViewData;
import io.github.guardjo.ticketmanager.common.domain.*;

import java.time.LocalDateTime;
import java.util.List;

public class TestDataGenerator {
    public static TicketViewData ticketViewData(long id) {
        return TicketViewData.create(
                id,
                "test",
                LocalDateTime.now(),
                "tester",
                10,
                false);
    }

    public static Program program() {
        return Program.builder()
                .name("Test Program")
                .build();
    }

    public static Member member() {
        return Member.builder()
                .name("Tester")
                .build();
    }

    public static Ticket ticket() {
        return Ticket.builder()
                .id(1L)
                .program(program())
                .remainingCount(10)
                .build();
    }

    public static Reservation reservation() {
        return Reservation.builder()
                .ticket(ticket())
                .startedTime(LocalDateTime.now())
                .member(member())
                .finishedTime(LocalDateTime.MAX)
                .build();
    }

    public static MemberGroup memberGroup() {
        return MemberGroup.builder()
                .id(1L)
                .groupName("testGroup")
                .members(List.of(member()))
                .build();
    }

    public static FreeTicket freeTicket(MemberGroup memberGroup) {
        return FreeTicket.builder()
                .id(1L)
                .ticket(ticket())
                .memberGroup(memberGroup)
                .build();
    }
}
