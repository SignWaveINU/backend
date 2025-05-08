package com.signwave.signwave.service;

import com.signwave.signwave.dto.HospitalReservationRequest;
import com.signwave.signwave.dto.HospitalReservationResponse;
import com.signwave.signwave.entity.HospitalReservation;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.repository.HospitalReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalReservationService {

    private final HospitalReservationRepository reservationRepository;

    @Transactional
    public HospitalReservationResponse reserve(Member member, HospitalReservationRequest request) {
        HospitalReservation reservation = HospitalReservation.builder()
                .member(member)
                .hospitalName(request.getHospitalName())
                .reservationTime(request.getReservationTime())
                .build();

        HospitalReservation saved = reservationRepository.save(reservation);
        return new HospitalReservationResponse(saved.getId(), saved.getHospitalName(), saved.getReservationTime().toString());
    }

    @Transactional(readOnly = true)
    public List<HospitalReservationResponse> getReservations(Member member) {
        return reservationRepository.findByMember(member).stream()
                .map(r -> new HospitalReservationResponse(r.getId(), r.getHospitalName(), r.getReservationTime().toString()))
                .collect(Collectors.toList());
    }
}