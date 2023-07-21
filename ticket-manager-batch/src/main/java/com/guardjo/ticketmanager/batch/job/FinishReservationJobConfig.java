package com.guardjo.ticketmanager.batch.job;

import com.guardjo.ticketmanager.batch.domain.Reservation;
import com.guardjo.ticketmanager.batch.domain.Ticket;
import com.guardjo.ticketmanager.batch.domain.TicketStatus;
import com.guardjo.ticketmanager.batch.repository.ReservationRepository;
import com.guardjo.ticketmanager.batch.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Map;

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
                .<Reservation, Ticket> chunk(CHUNK_SIZE)
                .reader(finishedReservationReader())
                .processor(finishedReservationProcessor())
                .writer(updateTicketWriter())
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
    public ItemWriter<Ticket> updateTicketWriter() {
        return new JpaItemWriterBuilder<Ticket>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
