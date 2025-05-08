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
    private LocalDate reservationDate;
    private String reservationTime;
}