package com.signwave.signwave.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class HospitalReservationRequest {
    private String hospitalName;
    private LocalDateTime reservationTime;
}