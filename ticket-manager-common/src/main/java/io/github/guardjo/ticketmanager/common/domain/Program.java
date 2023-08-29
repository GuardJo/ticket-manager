package io.github.guardjo.ticketmanager.common.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "program")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Program extends MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id")
    private Long id;
    @Setter
    @Column(length = 100, nullable = false)
    private String name;
    @Setter
    private Integer count;
    @Setter
    private Integer expirationPeriod;

    @OneToMany(mappedBy = "program", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id")
    @ToString.Exclude
    private Collection<Ticket> tickets;
}
