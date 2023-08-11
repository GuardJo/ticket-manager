package com.guardjo.ticketmanager.batch.job.tasklet;

import com.guardjo.ticketmanager.batch.domain.ReservationHistory;
import com.guardjo.ticketmanager.batch.model.ReservationHistoryExportData;
import com.guardjo.ticketmanager.batch.repository.ReservationHistoryRepository;
import com.guardjo.ticketmanager.batch.util.HistoryDataFileExporter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Component
@StepScope
@Transactional(readOnly = true)
public class DailyHistoryDataExportTasklet extends AbstractHistortDataExportTasklet {
    private final ReservationHistoryRepository reservationHistoryRepository;

    @Value("#{jobParameters[from]}")
    private String currentDate;

    public DailyHistoryDataExportTasklet(HistoryDataFileExporter historyDataFileExporter,
                                         ReservationHistoryRepository reservationHistoryRepository) {
        super(historyDataFileExporter);
        this.reservationHistoryRepository = reservationHistoryRepository;
    }

    @Override
    ReservationHistoryExportData generateExportData() {
        LocalDate from = LocalDate.parse(currentDate);

        Optional<ReservationHistory> reservationHistory = reservationHistoryRepository.findByHistoryDate(from);

        return reservationHistory.map(ReservationHistoryExportData::convert).orElse(null);
    }
}
