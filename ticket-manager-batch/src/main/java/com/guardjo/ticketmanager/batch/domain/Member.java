package com.guardjo.ticketmanager.batch.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Member extends MetaData{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Setter
    @Column(length = 100, nullable = false)
    private String name;
    @Setter
    @Column(length = 50, nullable = false)
    private String status;
    @Setter
    @Column(length = 50)
    private String phoneNumber;
    @Setter
    @Column(length = 100)
    private String email;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id")
    @ToString.Exclude
    private Collection<Ticket> tickets;
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id")
    @ToString.Exclude
    private Collection<Reservation> reservations;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "member_group_member",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Collection<MemberGroup> groups;
}
