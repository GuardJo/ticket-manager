package io.github.guardjo.ticketmanager.batch.job;

import io.github.guardjo.ticketmanager.batch.job.tasklet.FreeTicketBatchTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FreeTicketJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final FreeTicketBatchTasklet freeTicketBatchTasklet;

    @Bean
    public Job freeTicketJob() {
        return jobBuilderFactory.get("freeTicketJob")
                .start(freeTicketStep())
                .build();
    }

    @Bean
    public Step freeTicketStep() {
        return stepBuilderFactory.get("freeTicketStep")
                .tasklet(freeTicketBatchTasklet)
                .build();
    }
}
