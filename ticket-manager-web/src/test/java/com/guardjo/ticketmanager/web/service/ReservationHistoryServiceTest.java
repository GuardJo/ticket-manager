package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.StatisticsChartData;
import com.guardjo.ticketmanager.web.util.TestDataGenerator;
import io.github.guardjo.ticketmanager.common.domain.ReservationHistory;
import io.github.guardjo.ticketmanager.common.repository.ReservationHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ReservationHistoryServiceTest {
    @Mock
    private ReservationHistoryRepository historyRepository;

    @InjectMocks
    private ReservationHistoryService reservationHistoryService;

    @DisplayName("예약 현황 목록 반환 테스트")
    @Test
    void testFindStatisticsChartData() {
        List<ReservationHistory> expectedEntity = List.of(TestDataGenerator.reservationHistory(1L),
                TestDataGenerator.reservationHistory(2L));
        StatisticsChartData expected = StatisticsChartData.from(expectedEntity);

        given(historyRepository.findAll()).willReturn(expectedEntity);

        StatisticsChartData actual = reservationHistoryService.findStatisticsChartData();

        assertThat(actual).isEqualTo(expected);

        then(historyRepository).should().findAll();
    }
}