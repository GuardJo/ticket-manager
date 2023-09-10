package com.guardjo.ticketmanager.web.data;

import io.github.guardjo.ticketmanager.common.domain.FreeTicket;

import java.time.LocalDateTime;

public record FreeTicketViewData(
        long freeTicketId,
        String programName,
        String userGroupName,
        String status,
        LocalDateTime createdTime
) {
    public static FreeTicketViewData create(long freeTicketId, String programName, String userGroupName,
                                            String status, LocalDateTime createdTime) {
        return new FreeTicketViewData(freeTicketId, programName, userGroupName, status, createdTime);
    }

    public static FreeTicketViewData from(FreeTicket freeTicket) {
        return FreeTicketViewData.create(
                freeTicket.getId(),
                freeTicket.getProgram().getName(),
                freeTicket.getMemberGroup().getGroupName(),
                freeTicket.getStatus().name(),
                freeTicket.getCreatedTime()
        );
    }
}
