package com.guardjo.ticketmanager.web.data;

import io.github.guardjo.ticketmanager.batch.domain.Reservation;

import java.time.LocalDateTime;

public record TicketViewData(
        long ticketId,
        String programName,
        LocalDateTime startTime,
        String memberName,
        int remainingCount,
        boolean expired
) {
    public static TicketViewData create(long id, String programName, LocalDateTime startTime, String memberName,
                                        int remainingCount, boolean expired) {

        return new TicketViewData(id, programName, startTime, memberName, remainingCount, expired);
    }

    public static TicketViewData from(Reservation reservation) {
        return TicketViewData.create(
                reservation.getTicket().getId(),
                reservation.getTicket().getProgram().getName(),
                reservation.getStartedTime(),
                reservation.getMember().getName(),
                reservation.getTicket().getRemainingCount(),
                (reservation.getFinishedTime().isBefore(LocalDateTime.now()))
        );
    }
}
