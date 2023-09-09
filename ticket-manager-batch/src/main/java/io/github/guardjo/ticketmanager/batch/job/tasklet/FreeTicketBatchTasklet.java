package io.github.guardjo.ticketmanager.batch.job.tasklet;

import io.github.guardjo.ticketmanager.common.domain.*;
import io.github.guardjo.ticketmanager.common.repository.FreeTicketRepository;
import io.github.guardjo.ticketmanager.common.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FreeTicketBatchTasklet implements Tasklet {
    private final FreeTicketRepository freeTicketRepository;
    private final TicketRepository ticketRepository;

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
        return new HashSet<>(group.getMembers());
    }

    private void setFreeTicket(Member member, FreeTicket freeTicket) {
        Ticket newTicket = newTicket(freeTicket.getTicket(), member);

        if (member.getTickets().contains(newTicket)) {
            log.warn("Already received FreeTicket");
        } else {
            ticketRepository.save(newTicket);
        }
    }

    private Ticket newTicket(Ticket ticketInfo, Member member) {
        return Ticket.builder()
                .remainingCount(ticketInfo.getRemainingCount())
                .status(ticketInfo.getStatus())
                .startedTime(ticketInfo.getStartedTime())
                .expiredTime(ticketInfo.getExpiredTime())
                .member(member)
                .program(ticketInfo.getProgram())
                .build();
    }

    private Ticket newTicket(Program program, Member member, LocalDateTime now) {
        return Ticket.builder()
                .remainingCount(program.getCount() != null ? program.getCount() : program.getExpirationPeriod())
                .status(TicketStatus.READY)
                .startedTime(now)
                .expiredTime(now.plusYears(1L))
                .member(member)
                .program(program)
                .build();
    }
}
