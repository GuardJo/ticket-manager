package com.guardjo.ticketmanager.batch.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ReservationHistory extends MetaData{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_history_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private LocalDateTime historyDate;
    @Setter
    @Column
    private int totalNewReservationCount = 0;
    @Setter
    @Column
    private int totalReservationUsedCount = 0;
}
