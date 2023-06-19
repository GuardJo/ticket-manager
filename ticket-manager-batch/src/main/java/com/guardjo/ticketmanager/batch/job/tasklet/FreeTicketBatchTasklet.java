package com.guardjo.ticketmanager.batch.job.tasklet;

import com.guardjo.ticketmanager.batch.domain.*;
import com.guardjo.ticketmanager.batch.repository.FreeTicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FreeTicketBatchTasklet implements Tasklet {
    private final FreeTicketRepository freeTicketRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Starting Batch Free Tickets");

        List<FreeTicket> freeTickets = getBatchingFreeTicketList();

        for (FreeTicket freeTicket : freeTickets) {
            MemberGroup group = freeTicket.getMemberGroup();
            Set<Member> members = getMemberIn(group);

            members.forEach(member -> setFreeTicket(member, freeTicket));

            freeTicket.setStatus(FreeTicketStatus.RECEIVED);
            log.info("Received FreeTicket");
        }

        return RepeatStatus.FINISHED;
    }

    private List<FreeTicket> getBatchingFreeTicketList() {
        LocalDateTime currentTime = LocalDateTime.now();

        return freeTicketRepository.findByStatus(FreeTicketStatus.NOT_RECEIVE).stream()
                .filter(freeTicket -> freeTicket.getTicket().getStartedTime().isBefore(currentTime))
                .toList();
    }

    private Set<Member> getMemberIn(MemberGroup group) {
        Collection<MemberGroupMember> members = group.getMembers();

        return members.stream()
                .map(MemberGroupMember::getMember)
                .collect(Collectors.toSet());
    }

    private void setFreeTicket(Member member, FreeTicket freeTicket) {
        Ticket ticket = freeTicket.getTicket();

        if (member.getTickets().contains(ticket)) {
            log.warn("Already received FreeTicket");
        } else {
            member.getTickets().add(ticket);
        }
    }
}