package com.guardjo.ticketmanager.batch.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "member_group_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class MemberGroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_group_member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private MemberGroup group;
}
