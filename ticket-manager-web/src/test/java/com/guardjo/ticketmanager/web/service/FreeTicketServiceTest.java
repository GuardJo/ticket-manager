package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.FreeTicketViewData;
import com.guardjo.ticketmanager.web.util.TestDataGenerator;
import io.github.guardjo.ticketmanager.common.domain.FreeTicket;
import io.github.guardjo.ticketmanager.common.domain.MemberGroup;
import io.github.guardjo.ticketmanager.common.domain.Program;
import io.github.guardjo.ticketmanager.common.domain.Ticket;
import io.github.guardjo.ticketmanager.common.repository.FreeTicketRepository;
import io.github.guardjo.ticketmanager.common.repository.MemberGroupRepository;
import io.github.guardjo.ticketmanager.common.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class FreeTicketServiceTest {
    @Mock
    private FreeTicketRepository freeTicketRepository;

    @InjectMocks
    private FreeTicketService freeTicketService;

    @DisplayName("무료 이용권 발급 현황 목록 반환 테스트")
    @Test
    void testFindFreeTicketViewDataList() {
        MemberGroup memberGroup = TestDataGenerator.memberGroup();
        List<FreeTicket> expectedEntity = List.of(TestDataGenerator.freeTicket(memberGroup));
        List<FreeTicketViewData> expected = expectedEntity.stream()
                .map(FreeTicketViewData::from)
                .toList();

        given(freeTicketRepository.findAll()).willReturn(expectedEntity);

        List<FreeTicketViewData> actual = freeTicketService.findFreeTicketViewDataList();

        assertThat(actual).isEqualTo(expected);

        then(freeTicketRepository).should().findAll();
    }

    @DisplayName("신규 무료 이용권 발급 정보 저장 테스트")
    @Test
    void testSaveNewFreeTickets() {
        Program program = TestDataGenerator.program();
        MemberGroup memberGroup = TestDataGenerator.memberGroup();
        FreeTicket expected = TestDataGenerator.freeTicket(memberGroup);

        given(freeTicketRepository.save(any(FreeTicket.class))).willReturn(expected);

        assertThatCode(() -> freeTicketService.saveNewFreeTickets(program, memberGroup))
                .doesNotThrowAnyException();

        then(freeTicketRepository).should().save(any(FreeTicket.class));
    }
}