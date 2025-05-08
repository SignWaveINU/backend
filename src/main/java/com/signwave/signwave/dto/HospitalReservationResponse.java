package com.signwave.signwave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HospitalReservationResponse {
    private Long reservationId;
    private String hospitalName;
    private String reservationTime;
}