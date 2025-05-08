package com.signwave.signwave.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class HospitalReservationResponse {
    private Long reservationId;
    private String hospitalName;
    private String reservationDate; // "2025-05-09"
    private String reservationTime;    // "15:30"
}
