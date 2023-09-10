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
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
        List<ReservationHistory> expectedEntity = new ArrayList<>();
        expectedEntity.add(TestDataGenerator.reservationHistory(1L));
        expectedEntity.add(TestDataGenerator.reservationHistory(2L));
        StatisticsChartData expected = StatisticsChartData.from(expectedEntity);
        Collections.reverse(expectedEntity);

        given(historyRepository.findRecentlyData(any(Pageable.class))).willReturn(expectedEntity);

        StatisticsChartData actual = reservationHistoryService.findStatisticsChartData();

        assertThat(actual).isEqualTo(expected);

        then(historyRepository).should().findRecentlyData(any(Pageable.class));
    }
}