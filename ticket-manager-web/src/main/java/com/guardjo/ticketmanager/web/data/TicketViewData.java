package com.guardjo.ticketmanager.web.data;

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
}
