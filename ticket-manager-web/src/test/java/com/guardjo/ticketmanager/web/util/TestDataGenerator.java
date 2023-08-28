package com.guardjo.ticketmanager.web.util;

import com.guardjo.ticketmanager.web.data.TicketViewData;
import io.github.guardjo.ticketmanager.batch.domain.Member;
import io.github.guardjo.ticketmanager.batch.domain.Program;
import io.github.guardjo.ticketmanager.batch.domain.Reservation;
import io.github.guardjo.ticketmanager.batch.domain.Ticket;

import java.time.LocalDateTime;

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
}
