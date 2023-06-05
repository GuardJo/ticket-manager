package com.guardjo.ticketmanager.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableBatchProcessing
@RequiredArgsConstructor
public class TicketManagerBatchApplication {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step testStep() {
        return this.stepBuilderFactory.get("testStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("Step Test!");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Job testJob() {
        return jobBuilderFactory.get("testJob")
                .start(testStep())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(TicketManagerBatchApplication.class, args);
    }
}
