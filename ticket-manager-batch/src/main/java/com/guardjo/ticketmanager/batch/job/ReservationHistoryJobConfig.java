package com.guardjo.ticketmanager.batch.job;

import com.guardjo.ticketmanager.batch.job.tasklet.ReservationHistoryBatchTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReservationHistoryJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ReservationHistoryBatchTasklet reservationHistoryBatchTasklet;

    @Bean
    public Job initReservationHistoryJob() {
        return jobBuilderFactory.get("initReservationHistoryJob")
                .start(initReservationHistoryStep())
                .build();
    }

    @Bean
    @StepScope
    public Step initReservationHistoryStep() {
        return stepBuilderFactory.get("initReservationHistoryStep")
                .tasklet(reservationHistoryBatchTasklet)
                .build();
    }
}
