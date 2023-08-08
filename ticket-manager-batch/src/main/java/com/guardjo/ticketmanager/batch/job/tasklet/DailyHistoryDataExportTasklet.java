package com.guardjo.ticketmanager.batch.job.tasklet;

import com.guardjo.ticketmanager.batch.domain.ReservationHistory;
import com.guardjo.ticketmanager.batch.model.ReservationHistoryExportData;
import com.guardjo.ticketmanager.batch.repository.ReservationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DailyHistoryDataExportTasklet extends AbstractHistortDataExportTasklet {
    private final ReservationHistoryRepository reservationHistoryRepository;

    @Value("#{jobParameters[from]}")
    private String currentDate;


    @Override
    ReservationHistoryExportData generateExportData() {
        LocalDate from = LocalDate.parse(currentDate);

        Optional<ReservationHistory> reservationHistory = reservationHistoryRepository.findByHistoryDate(from);

        return reservationHistory.map(ReservationHistoryExportData::convert).orElse(null);
    }
}
