package io.github.guardjo.ticketmanager.batch.job;

import io.github.guardjo.ticketmanager.common.domain.Reservation;
import io.github.guardjo.ticketmanager.common.domain.Ticket;
import io.github.guardjo.ticketmanager.common.domain.TicketStatus;
import io.github.guardjo.ticketmanager.common.repository.ReservationRepository;
import io.github.guardjo.ticketmanager.common.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.Future;

@Configuration
@RequiredArgsConstructor
public class FinishReservationJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final TicketRepository ticketRepository;
    private final ReservationRepository reservationRepository;

    private final static int CHUNK_SIZE = 10;

    @Bean
    public Job finishedReservationJob() {
        return jobBuilderFactory.get("finishedReservationJob")
                .start(finishedReservationStep())
                .build();
    }

    @Bean
    public Step finishedReservationStep() {
        return stepBuilderFactory.get("finishedReservationStep")
                .<Reservation, Future<Ticket>>chunk(CHUNK_SIZE)
                .reader(finishedReservationReader())
                .processor(asyncFinishedReservationProcessor())
                .writer(asyncUpdateTicketWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public JpaCursorItemReader<Reservation> finishedReservationReader() {
        return new JpaCursorItemReaderBuilder<Reservation>()
                .name("finishedReservationReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select r\n" +
                        "from Reservation r\n" +
                        "join fetch r.ticket\n" +
                        "where r.finishedTime < :finishedTime")
                .parameterValues(Map.of("finishedTime", LocalDateTime.now()))
                .build();
    }

    @Bean
    public AsyncItemProcessor<Reservation, Ticket> asyncFinishedReservationProcessor() {
        AsyncItemProcessor<Reservation, Ticket> asyncItemProcessor = new AsyncItemProcessor<>();
        asyncItemProcessor.setDelegate(finishedReservationProcessor());
        asyncItemProcessor.setTaskExecutor(new SimpleAsyncTaskExecutor());

        return asyncItemProcessor;
    }

    @Bean
    public ItemProcessor<Reservation, Ticket> finishedReservationProcessor() {
        return (reservation) -> {
            reservation.setUsedCount(reservation.getUsedCount() + 1);
            reservationRepository.save(reservation);

            Ticket ticket = reservation.getTicket();

            if (ticket.getStatus().equals(TicketStatus.READY)) {
                ticket.setStatus(TicketStatus.PROGRESS);
            }

            ticket.setRemainingCount(ticket.getRemainingCount() - 1);

            return ticket;
        };
    }

    @Bean
    public AsyncItemWriter<Ticket> asyncUpdateTicketWriter() {
        AsyncItemWriter<Ticket> asyncItemWriter = new AsyncItemWriter<>();
        asyncItemWriter.setDelegate(updateTicketWriter());

        return asyncItemWriter;
    }

    @Bean
    public ItemWriter<Ticket> updateTicketWriter() {
        return new JpaItemWriterBuilder<Ticket>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
