package io.github.guardjo.ticketmanager.batch.job.tasklet;

import io.github.guardjo.ticketmanager.common.domain.Reservation;
import io.github.guardjo.ticketmanager.common.domain.ReservationHistory;
import io.github.guardjo.ticketmanager.common.repository.ReservationHistoryRepository;
import io.github.guardjo.ticketmanager.common.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@StepScope
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ReservationHistoryBatchTasklet implements Tasklet {
    private final ReservationRepository reservationRepository;
    private final ReservationHistoryRepository reservationHistoryRepository;

    @Value("#{jobParameters[from]}")
    private String startDateStr;
    @Value("#{jobParameters[to]}")
    private String finishDateStr;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Starting Calculate Reservation Histories...");

        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate finishData = LocalDate.parse(finishDateStr);
        LocalDate currentDate = startDate;

        do {
            if (!reservationHistoryRepository.existsByHistoryDate(currentDate)) {
                ReservationHistory reservationHistory = initReservationHistory(currentDate);
                reservationHistoryRepository.save(reservationHistory);
            }
            currentDate = currentDate.plusDays(1L);
        } while (currentDate.isBefore(finishData));

        log.info("Finished Calculate Reservation Histories");

        return RepeatStatus.FINISHED;
    }

    @Transactional(readOnly = true)
    public ReservationHistory initReservationHistory(LocalDate historyDate) {
        LocalDateTime from = historyDate.minusDays(1L).atStartOfDay();
        LocalDateTime to = historyDate.atStartOfDay();
        List<Reservation> reservationList = reservationRepository
                .findAllByStartedTimeGreaterThanEqualAndFinishedTimeLessThanEqual(from, to);

        int totalNewCount = reservationRepository.findAllByTodayNewReservations(from, to).size();
        int totalUsedCount = 0;

        for (Reservation reservation : reservationList) {
            totalUsedCount += reservation.getUsedCount();
        }

        return ReservationHistory.builder()
                .historyDate(historyDate)
                .totalReservationUsedCount(totalUsedCount)
                .totalNewReservationCount(totalNewCount)
                .build();
    }
}
