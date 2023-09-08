package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.TicketSimpleData;
import com.guardjo.ticketmanager.web.util.TestDataGenerator;
import io.github.guardjo.ticketmanager.common.domain.Ticket;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @DisplayName("전체 이용권 목록 반환 테스트")
    @Test
    void testFindAllTicketSimpleDataList() {
        List<Ticket> expectedEntity = List.of(TestDataGenerator.ticket());
        List<TicketSimpleData> expected = expectedEntity.stream()
                .map(TicketSimpleData::from)
                .toList();

        given(ticketRepository.findAll()).willReturn(expectedEntity);

        List<TicketSimpleData> actual = ticketService.findAllTicketSimpleDataList();

        assertThat(actual).isEqualTo(expected);

        then(ticketRepository).should().findAll();
    }

    @DisplayName("이용권 단일 조회 테스트")
    @ParameterizedTest
    @MethodSource("ticketFindTestData")
    void testFindTicket(Optional<Ticket> expected) {
        long ticketId = expected.isPresent() ? expected.get().getId() : 1L;

        given(ticketRepository.findById(eq(ticketId))).willReturn(expected);

        Ticket actual = ticketService.findTicket(ticketId);

        if (expected.isPresent()) {
            assertThat(actual).isEqualTo(expected.get());
        } else {
            assertThat(actual).isNull();
        }

        then(ticketRepository).should().findById(eq(ticketId));
    }

    static Stream<Arguments> ticketFindTestData() {
        return Stream.of(
                Arguments.of(Optional.of(TestDataGenerator.ticket())),
                Arguments.of(Optional.empty())
        );
    }
}