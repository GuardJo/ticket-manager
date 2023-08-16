package io.github.guardjo.ticketmanager.batch.model;

import io.github.guardjo.ticketmanager.batch.domain.ReservationHistory;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;

public record ReservationHistoryExportData(
        String dataName,
        HistoryDataType historyDataType,
        int totalNewReservationCount,
        int totalReservationUsedCount
) {
    public static ReservationHistoryExportData convert(ReservationHistory reservationHistory) {
        String name = reservationHistory.getHistoryDate().toString() + "_DailyHistory";

        return new ReservationHistoryExportData(
                name,
                HistoryDataType.DAILY,
                reservationHistory.getTotalNewReservationCount(),
                reservationHistory.getTotalReservationUsedCount()
        );
    }

    public static ReservationHistoryExportData convert(List<ReservationHistory> reservationHistories) {
        if (reservationHistories.isEmpty()) {
            return null;
        }

        LocalDate base = reservationHistories.get(0).getHistoryDate();

        int year = base.getYear();
        int week = base.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);

        String name = year + "_" + week + "주차_WeeklyHistory";
        int totalNew = 0;
        int totalUsed = 0;

        for (ReservationHistory reservationHistory : reservationHistories) {
            totalUsed += reservationHistory.getTotalReservationUsedCount();
            totalUsed += reservationHistory.getTotalNewReservationCount();
        }

        return new ReservationHistoryExportData(
                name,
                HistoryDataType.WEEKLY,
                totalNew,
                totalUsed
        );
    }
}
