package io.github.guardjo.ticketmanager.batch.job.tasklet;

import io.github.guardjo.ticketmanager.common.domain.ReservationHistory;
import io.github.guardjo.ticketmanager.batch.model.ReservationHistoryExportData;
import io.github.guardjo.ticketmanager.common.repository.ReservationHistoryRepository;
import io.github.guardjo.ticketmanager.batch.util.HistoryDataFileExporter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@StepScope
@Transactional(readOnly = true)
public class WeeklyHistoryDataExportTasklet extends AbstractHistortDataExportTasklet {
    private final ReservationHistoryRepository reservationHistoryRepository;

    public WeeklyHistoryDataExportTasklet(HistoryDataFileExporter historyDataFileExporter,
                                          ReservationHistoryRepository reservationHistoryRepository) {
        super(historyDataFileExporter);
        this.reservationHistoryRepository = reservationHistoryRepository;
    }

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
