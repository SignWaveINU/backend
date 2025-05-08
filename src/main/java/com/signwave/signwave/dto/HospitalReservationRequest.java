package com.signwave.signwave.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class HospitalReservationRequest {
    private String hospitalName;
    private LocalDate reservationDate;
    private LocalDateTime reservationTime;
}