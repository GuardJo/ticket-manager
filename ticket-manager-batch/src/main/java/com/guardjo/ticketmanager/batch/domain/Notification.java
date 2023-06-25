package com.guardjo.ticketmanager.batch.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Notification extends MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private long id;
    @Setter
    @Column(length = 200, nullable = false)
    private String cootent;
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "notification_status", nullable = false)
    private NotificationStatus status = NotificationStatus.NOT_SEND;
    @Setter
    @Column(name = "kakao_uuid", nullable = false)
    private String kakaoUUID;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
