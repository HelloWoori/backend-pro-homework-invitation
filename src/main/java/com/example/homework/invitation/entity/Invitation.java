package com.example.homework.invitation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(
        indexes = {
                @Index(name = "idx_receiver", columnList = "receiverUid"),
                @Index(name = "idx_group", columnList = "groupUid"),
                @Index(name = "idx_auth", columnList = "invitationAuth"),
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_sender_receiver_group", columnNames = {"senderUid", "receiverUid", "groupUid"})
        }
)
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitationUid;

    @Column(nullable = false)
    private Long senderUid;

    @Column(nullable = false)
    private Long receiverUid;

    @Column(nullable = false)
    private Long groupUid;

    @Column(nullable = false, length = 1000)
    private String invitationAuth;

    @Column(nullable = false, columnDefinition="TINYINT(4)")
    @ColumnDefault("0")
    private boolean isInvited;

    @Column(nullable = false)
    private LocalDateTime datetimeAdd;

    private LocalDateTime datetimeInvited;

    @Column(nullable = false)
    private LocalDateTime datetimeExpired;
}
