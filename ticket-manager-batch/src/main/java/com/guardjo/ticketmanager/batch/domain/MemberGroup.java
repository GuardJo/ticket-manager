package com.guardjo.ticketmanager.batch.domain;

import lombok.*;
import org.springframework.core.annotation.Order;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "member_group")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberGroup extends MetaData{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;
    @Setter
    @Column(nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @OrderBy("id")
    private Collection<MemberGroupMember> members;
    @OneToMany(mappedBy = "memberGroup", orphanRemoval = true)
    private Collection<FreeTicket> freeTickets;
}
