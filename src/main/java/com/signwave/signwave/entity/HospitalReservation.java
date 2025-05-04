package com.signwave.signwave.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "hospital_reservations")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class HospitalReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String hospitalName;

    @Column(nullable = false)
    private LocalDateTime reservationTime;

    public void update(String hospitalName, LocalDateTime reservationTime) {
        this.hospitalName = hospitalName;
        this.reservationTime = reservationTime;
    }
}

