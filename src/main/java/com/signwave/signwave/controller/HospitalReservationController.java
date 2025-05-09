package com.signwave.signwave.controller;

import com.signwave.signwave.dto.HospitalReservationRequest;
import com.signwave.signwave.dto.HospitalReservationResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.repository.MemberRepository;
import com.signwave.signwave.service.HospitalReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class HospitalReservationController {

    private final HospitalReservationService reservationService;
    private final MemberRepository memberRepository;

    @PostMapping
    @Operation(summary = "병원 예약 생성", description = "로그인한 사용자가 병원 예약을 생성합니다.")
    public ResponseEntity<HospitalReservationResponse> createReservation(
            @RequestBody HospitalReservationRequest request,
            Authentication authentication) {

        String email = (String) authentication.getPrincipal();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자를 찾을 수 없습니다."));

        HospitalReservationResponse response = reservationService.createReservation(request, member);
        return ResponseEntity.ok(response);
    }
}
