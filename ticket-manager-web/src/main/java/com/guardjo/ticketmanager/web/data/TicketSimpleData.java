package com.guardjo.ticketmanager.web.data;

import io.github.guardjo.ticketmanager.common.domain.Ticket;

public record TicketSimpleData(
        long ticketId,
        String programName
) {
    public static TicketSimpleData create(long ticketId, String programName) {
        return new TicketSimpleData(ticketId, programName);
    }

    public static TicketSimpleData from(Ticket ticket) {
        return TicketSimpleData.create(ticket.getId(), ticket.getProgram().getName());
    }
}
