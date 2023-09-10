package com.guardjo.ticketmanager.web.data;

import io.github.guardjo.ticketmanager.common.domain.ReservationHistory;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record StatisticsChartData(
        List<String> labels,
        List<Long> reservationCountList,
        List<Long> usedCountList,
        long totalReservation,
        long totalUsed
) {
    public static StatisticsChartData of(List<String> labels, List<Long> reservationCountList,
                                         List<Long> usedCountList) {
        return new StatisticsChartData(
                labels,
                reservationCountList,
                usedCountList,
                reservationCountList.stream()
                        .reduce(Long::sum)
                        .orElse(0L),
                usedCountList.stream()
                        .reduce(Long::sum)
                        .orElse(0L)
        );
    }

    public static StatisticsChartData from(List<ReservationHistory> reservationHistories) {
        Collections.reverse(reservationHistories);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return StatisticsChartData.of(
                reservationHistories.stream()
                        .map(reservationHistory -> reservationHistory.getHistoryDate().format(formatter))
                        .collect(Collectors.toList()),
                reservationHistories.stream()
                        .mapToLong(ReservationHistory::getTotalNewReservationCount)
                        .boxed().toList(),
                reservationHistories.stream()
                        .mapToLong(ReservationHistory::getTotalReservationUsedCount)
                        .boxed().toList()
        );
    }
}
