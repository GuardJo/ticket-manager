package com.guardjo.ticketmanager.batch.domain;

import lombok.*;

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

    @ManyToMany
    @JoinTable(
            name = "member_group_member",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Collection<Member> members;
}
