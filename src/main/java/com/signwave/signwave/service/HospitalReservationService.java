package com.signwave.signwave.service;

import com.signwave.signwave.dto.HospitalReservationRequest;
import com.signwave.signwave.dto.HospitalReservationResponse;
import com.signwave.signwave.entity.HospitalReservation;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.repository.HospitalReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class HospitalReservationService {

    private final HospitalReservationRepository reservationRepository;

    public HospitalReservationResponse createReservation(HospitalReservationRequest request, Member member) {
        HospitalReservation reservation = HospitalReservation.builder()
                .hospitalName(request.getHospitalName())
                .reservationDate(request.getReservationDate())
                .reservationTime(request.getReservationTime())
                .member(member)
                .build();

        HospitalReservation saved = reservationRepository.save(reservation);

        String formattedDate = saved.getReservationDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // HH:mm 형식으로 포맷 (예: 15:30)
        String formattedTime = saved.getReservationTime()
                .toLocalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm"));

        return HospitalReservationResponse.builder()
                .reservationId(saved.getId())
                .hospitalName(saved.getHospitalName())
                .reservationDate(formattedDate)
                .reservationTime(formattedTime)
                .build();

    }
}
