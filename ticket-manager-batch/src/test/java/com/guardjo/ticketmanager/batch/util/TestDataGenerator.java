package com.guardjo.ticketmanager.batch.util;

import com.guardjo.ticketmanager.batch.domain.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestDataGenerator {
    public static Member member(long id, String name) {
        return Member.builder()
                .id(id)
                .name(name)
                .status("TEST")
                .phoneNumber("000-0000-0000")
                .email("test@mail.com")
                .tickets(new ArrayList<>())
                .build();
    }

    public static MemberGroup memberGroup(String name) {
        return MemberGroup.builder()
                .groupName("test group")
                .members(List.of(
                        MemberGroupMember.builder()
                                .member(member(1L, "tester"))
                                .build()
                ))
                .build();
    }

    public static Program program() {
        return Program.builder()
                .id(1L)
                .name("Test Program")
                .count(1)
                .build();
    }

    public static Ticket ticket() {
        return Ticket.builder()
                .remainingCount(1)
                .status(TicketStatus.READY)
                .startedTime(LocalDateTime.now())
                .expiredTime(LocalDateTime.MAX)
                .program(program())
                .build();
    }

    public static FreeTicket freeTicket(MemberGroup memberGroup, Ticket ticket) {
        return FreeTicket.builder()
                .status(FreeTicketStatus.MOT_RECEIVE)
                .ticket(ticket)
                .memberGroup(memberGroup)
                .build();
    }
}
