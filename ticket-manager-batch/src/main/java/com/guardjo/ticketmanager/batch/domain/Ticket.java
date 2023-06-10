package com.guardjo.ticketmanager.batch.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "ticket")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Ticket extends MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long id;
    private int remainingCount;
    @Column(nullable = false)
    private LocalDateTime startedTime;
    @Column(nullable = false)
    private LocalDateTime expiredTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id")
    @ToString.Exclude
    private Collection<Reservation> reservations;
    @ManyToOne(optional = false)
    @JoinColumn(name = "program_id")
    private Program program;
}
