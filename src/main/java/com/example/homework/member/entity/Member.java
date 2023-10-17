package com.example.homework.member.entity;

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
@Table(indexes = @Index(name = "idx_email", columnList = "email"))
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberUid;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 12)
    private String phone;

    @Column(nullable = false, columnDefinition="TINYINT(4)")
    @ColumnDefault("0")
    private boolean status;

    private LocalDateTime datetimeAdd;
    private LocalDateTime datetimeMod;
}
