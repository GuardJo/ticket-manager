package io.github.guardjo.ticketmanager.batch.job;

import io.github.guardjo.ticketmanager.common.domain.Ticket;
import io.github.guardjo.ticketmanager.common.domain.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ExpireTicketJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private final static int CHUNK_SIZE = 5;

    @Bean
    public Job expireTicketJob() {
        return jobBuilderFactory.get("expireTicketJob")
                .start(expireTicketStep())
                .build();
    }

    @Bean
    public Step expireTicketStep() {
        return stepBuilderFactory.get("expireTicketStep")
                .<Ticket, Ticket>chunk(CHUNK_SIZE)
                .reader(ticketItemReader())
                .processor(ticketItemProcessor())
                .writer(ticketItemWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    @StepScope
    public JpaCursorItemReader<Ticket> ticketItemReader() {
        return new JpaCursorItemReaderBuilder<Ticket>()
                .name("ticketItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select t from Ticket t where (t.status = :ready or t.status = :progress) and t.expiredTime <= :expiredTime")
                .parameterValues(
                        Map.of(
                                "ready", TicketStatus.READY,
                                "progress", TicketStatus.PROGRESS,
                                "expiredTime", LocalDateTime.now()))
                .build();
    }

    @Bean
    public ItemProcessor<Ticket, Ticket> ticketItemProcessor() {
        return (ticket -> {
            ticket.setStatus(TicketStatus.EXPIRED);
            ticket.setExpiredTime(LocalDateTime.now());
            return ticket;
        });
    }

    @Bean
    public JpaItemWriter<Ticket> ticketItemWriter() {
        return new JpaItemWriterBuilder<Ticket>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
