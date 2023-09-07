package com.guardjo.ticketmanager.web.service;

import com.guardjo.ticketmanager.web.data.StatisticsChartData;
import io.github.guardjo.ticketmanager.common.domain.ReservationHistory;
import io.github.guardjo.ticketmanager.common.repository.ReservationHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReservationHistoryService {
    private final ReservationHistoryRepository reservationHistoryRepository;

    /**
     * 현재까지 기록된 일자별 예약 현황 목록을 반환한다.
     *
     * @return ReservationHistory Entity List
     */
    @Transactional(readOnly = true)
    public StatisticsChartData findStatisticsChartData() {
        log.info("Find StatisticsChartData");

        List<ReservationHistory> reservationHistories = reservationHistoryRepository.findAll();

        return StatisticsChartData.from(reservationHistories);
    }
}
