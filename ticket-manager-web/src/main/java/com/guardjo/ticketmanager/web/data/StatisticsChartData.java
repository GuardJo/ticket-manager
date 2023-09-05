package com.guardjo.ticketmanager.web.data;

import java.util.List;
import java.util.function.BinaryOperator;

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
}
