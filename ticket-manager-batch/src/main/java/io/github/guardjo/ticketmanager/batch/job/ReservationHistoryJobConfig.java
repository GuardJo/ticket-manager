package io.github.guardjo.ticketmanager.batch.job;

import io.github.guardjo.ticketmanager.batch.job.tasklet.DailyHistoryDataExportTasklet;
import io.github.guardjo.ticketmanager.batch.job.tasklet.ReservationHistoryBatchTasklet;
import io.github.guardjo.ticketmanager.batch.job.tasklet.WeeklyHistoryDataExportTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class ReservationHistoryJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ReservationHistoryBatchTasklet reservationHistoryBatchTasklet;
    private final DailyHistoryDataExportTasklet dailyHistoryDataExportTasklet;
    private final WeeklyHistoryDataExportTasklet weeklyHistoryDataExportTasklet;

    @Bean
    public Job initReservationHistoryJob() {
        Flow initReservationHistoryFlow = new FlowBuilder<Flow>("initReservationHistoryFlow")
                .start(initReservationHistoryStep())
                .build();

        Flow dailyReservationHistoryExportFlow = new FlowBuilder<Flow>("initReservationHistoryFlow")
                .start(dailyReservationHistoryExportStep())
                .build();

        Flow weeklyReservationHistoryExportFlow = new FlowBuilder<Flow>("weeklyReservationHistoryExportFlow")
                .start(weeklyReservationHistoryExportStep())
                .build();

        Flow multiFlowReservationHistoryExportFlow = new FlowBuilder<Flow>("multiFlowReservationHistoryExportFlow")
                .split(new SimpleAsyncTaskExecutor())
                .add(dailyReservationHistoryExportFlow, weeklyReservationHistoryExportFlow)
                .build();

        return jobBuilderFactory.get("initReservationHistoryJob")
                .start(initReservationHistoryFlow)
                .next(multiFlowReservationHistoryExportFlow)
                .build()
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step initReservationHistoryStep() {
        return stepBuilderFactory.get("initReservationHistoryStep")
                .tasklet(reservationHistoryBatchTasklet)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step dailyReservationHistoryExportStep() {
        return stepBuilderFactory.get("dailyReservationHistoryExportStep")
                .tasklet(dailyHistoryDataExportTasklet)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step weeklyReservationHistoryExportStep() {
        return stepBuilderFactory.get("weeklyReservationHistoryExportStep")
                .tasklet(weeklyHistoryDataExportTasklet)
                .allowStartIfComplete(true)
                .build();
    }
}
