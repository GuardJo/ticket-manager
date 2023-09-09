package io.github.guardjo.ticketmanager.batch.util;

import io.github.guardjo.ticketmanager.common.domain.*;
import io.github.guardjo.ticketmanager.batch.model.JobExecuteRequest;
import org.springframework.integration.support.PropertiesBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
                .members(List.of(member(1L, "tester")))
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
                .status(FreeTicketStatus.NOT_RECEIVE)
                .ticket(ticket)
                .memberGroup(memberGroup)
                .build();
    }

    public static Reservation reservation() {
        return Reservation.builder()
                .usedCount(0)
                .startedTime(LocalDateTime.now())
                .finishedTime(LocalDateTime.MAX)
                .member(member(999L, "tester"))
                .ticket(ticket())
                .build();
    }

    public static Notification notification(Reservation reservation) {
        return Notification.builder()
                .content("Test Notification")
                .status(NotificationStatus.NOT_SEND)
                .kakaoUUID("test-uuid")
                .reservation(reservation)
                .build();
    }

    public static ReservationHistory reservationHistory() {
        return ReservationHistory.builder()
                .historyDate(LocalDate.now())
                .totalNewReservationCount(10)
                .totalReservationUsedCount(10)
                .build();
    }

    public static JobExecuteRequest jobExecuteRequest() {
        return JobExecuteRequest.of("testJob", new PropertiesBuilder().put("from", "2023-03-03").get());
    }
}