package com.signwave.signwave.controller;

import com.signwave.signwave.dto.HospitalReservationRequest;
import com.signwave.signwave.dto.HospitalReservationResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.service.HospitalReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class HospitalReservationController {

    private final HospitalReservationService reservationService;

    @PostMapping
    @Operation(summary = "병원 예약 생성", description = "로그인한 사용자가 병원 예약을 생성합니다.")
    public ResponseEntity<HospitalReservationResponse> createReservation(
            @RequestBody HospitalReservationRequest request,
            Authentication authentication) {

        Member member = (Member) authentication.getPrincipal();

        HospitalReservationResponse response = reservationService.createReservation(request, member);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reservationId}")
    @Operation(summary = "병원 예약 삭제", description = "로그인한 사용자가 병원 예약을 삭제합니다.")
    public ResponseEntity<Map<String, String>> deleteReservation(
            @PathVariable Long reservationId,
            Authentication authentication) {

        Member member = (Member) authentication.getPrincipal();

        reservationService.deleteReservation(reservationId, member);

        Map<String, String> response = Map.of("message", "병원 예약이 성공적으로 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }
}
