package com.guardjo.ticketmanager.batch.job.tasklet;

import com.guardjo.ticketmanager.batch.model.ReservationHistoryExportData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public abstract class AbstractHistortDataExportTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Export Daily Reservation History Data");

        ReservationHistoryExportData exportData = generateExportData();

        export(exportData);

        return RepeatStatus.FINISHED;
    }

    abstract ReservationHistoryExportData generateExportData();

    private void export(ReservationHistoryExportData exportData) {
        System.out.println(exportData.toString());
    }
}
