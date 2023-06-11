package com.guardjo.ticketmanager.batch.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Reservation extends MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    @Setter
    private int usedCount;
    @Setter
    @Column(nullable = false)
    private LocalDateTime startedTime;
    @Setter
    @Column(nullable = false)
    private LocalDateTime finishedTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne(optional = false)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
