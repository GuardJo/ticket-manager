package com.guardjo.ticketmanager.batch.job.tasklet;

import com.guardjo.ticketmanager.batch.domain.ReservationHistory;
import com.guardjo.ticketmanager.batch.model.ReservationHistoryExportData;
import com.guardjo.ticketmanager.batch.repository.ReservationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WeeklyHistoryDataExportTasklet extends AbstractHistortDataExportTasklet {
    private final ReservationHistoryRepository reservationHistoryRepository;

    @Value("#{jobParameters[from]}")
    private String currentDate;

    @Override
    ReservationHistoryExportData generateExportData() {
        LocalDate to = LocalDate.parse(currentDate);
        LocalDate from = to.minusWeeks(1L);

        List<ReservationHistory> reservationHistories = reservationHistoryRepository.findWeeklyData(from, to);

        return ReservationHistoryExportData.convert(reservationHistories);
    }
}
