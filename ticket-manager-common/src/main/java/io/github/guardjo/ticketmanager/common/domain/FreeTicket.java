package io.github.guardjo.ticketmanager.common.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "free_ticket")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class FreeTicket extends MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_ticket_id")
    private Long id;
    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private FreeTicketStatus status = FreeTicketStatus.NOT_RECEIVE;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private MemberGroup memberGroup;
}
