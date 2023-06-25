package com.guardjo.ticketmanager.batch.job;

import com.guardjo.ticketmanager.batch.domain.Notification;
import com.guardjo.ticketmanager.batch.domain.NotificationStatus;
import com.guardjo.ticketmanager.batch.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ReservationAlarmJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private final static int CHUNK_SIZE = 10;

    @Bean
    public Job notificationCreateJob() {
        return jobBuilderFactory.get("notificationCreateJob")
                .start(notificationCreateStep())
                .build();
    }

    @Bean
    public Step notificationCreateStep() {
        return stepBuilderFactory.get("notificationCreateStep")
                .<Reservation, Notification>chunk(CHUNK_SIZE)
                .reader(reservationJpaPagingItemReader())
                .processor(notificationCreateProcessor())
                .writer(notificationJpaItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Reservation> reservationJpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Reservation>()
                .name("reservationJpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select r from Reservation r where r.startedTime <= :startedTime")
                .parameterValues(Map.of(
                        "startedTime", LocalDateTime.now().plusMinutes(10) // 10분 뒤 시작하는 예약
                ))
                .pageSize(CHUNK_SIZE)
                .build();
    }

    @Bean
    public ItemProcessor<Reservation, Notification> notificationCreateProcessor() {
        return ((reservation) -> {
            if (reservation.getNotification() != null) {
                return null;
            } else {
                return Notification.builder()
                        .status(NotificationStatus.NOT_SEND)
                        .cootent(String.format("예약하신 %s가 곧 시작됩니다.",
                                reservation.getTicket().getProgram().getName()))
                        .kakaoUUID("test") //TODO kakao uuid 가져오는거 추후 추가 예정
                        .reservation(reservation)
                        .build();
            }
        });
    }

    @Bean
    public JpaItemWriter<Notification> notificationJpaItemWriter() {
        return new JpaItemWriterBuilder<Notification>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
