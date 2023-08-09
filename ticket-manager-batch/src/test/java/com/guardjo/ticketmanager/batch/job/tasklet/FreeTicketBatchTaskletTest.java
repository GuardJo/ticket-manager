package com.guardjo.ticketmanager.batch.job.tasklet;

import com.guardjo.ticketmanager.batch.domain.FreeTicket;
import com.guardjo.ticketmanager.batch.domain.FreeTicketStatus;
import com.guardjo.ticketmanager.batch.domain.MemberGroup;
import com.guardjo.ticketmanager.batch.domain.Ticket;
import com.guardjo.ticketmanager.batch.repository.FreeTicketRepository;
import com.guardjo.ticketmanager.batch.repository.TicketRepository;
import com.guardjo.ticketmanager.batch.util.TestDataGenerator;
import org.hibernate.annotations.DiscriminatorFormula;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class FreeTicketBatchTaskletTest {
    @Mock
    private StepContribution stepContribution;
    @Mock
    private ChunkContext chunkContext;
    @Mock
    private FreeTicketRepository freeTicketRepository;
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private FreeTicketBatchTasklet freeTicketBatchTasklet;

    @BeforeEach
    void setUp() {
        MemberGroup memberGroup = TestDataGenerator.memberGroup("test group");
        Ticket ticket = TestDataGenerator.ticket();
        FreeTicket freeTicket = TestDataGenerator.freeTicket(memberGroup, ticket);
        ticket.setMember(memberGroup.getMembers().stream().findAny().get());

        given(freeTicketRepository.findByStatus(FreeTicketStatus.NOT_RECEIVE)).willReturn(List.of(freeTicket));
        given(ticketRepository.save(any(Ticket.class))).willReturn(ticket);
    }

    @AfterEach
    void tearDown() {
        then(freeTicketRepository).should().findByStatus(FreeTicketStatus.NOT_RECEIVE);
        then(ticketRepository).should().save(any(Ticket.class));
    }

    @DisplayName("무료 이용권 일괄 지급 batch 테스트")
    @Test
    void testTaskletExecute() throws Exception {
        RepeatStatus actual = freeTicketBatchTasklet.execute(stepContribution, chunkContext);

        assertThat(actual).isEqualTo(RepeatStatus.FINISHED);
    }
}