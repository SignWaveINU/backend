// TranslationHistoryController.java
package com.signwave.signwave.controller;

import com.signwave.signwave.dto.FavoriteRequest;
import com.signwave.signwave.dto.FavoriteResponse;
import com.signwave.signwave.dto.TranslationHistoryResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.repository.MemberRepository;
import com.signwave.signwave.service.TranslationHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class TranslationHistoryController {

    private final TranslationHistoryService historyService;
    private final MemberRepository memberRepository;

    @PostMapping("/favorite")
    @Operation(summary = "즐겨찾기 등록", description = "특정 번역기록을 즐겨찾기로 등록합니다.")
    public ResponseEntity<FavoriteResponse> addFavorite(@RequestBody FavoriteRequest request) {
        FavoriteResponse response = historyService.markAsFavorite(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "번역기록 전체 조회", description = "로그인한 사용자의 전체 번역기록을 조회합니다.")
    public ResponseEntity<List<TranslationHistoryResponse>> getAllHistories(@AuthenticationPrincipal String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        List<TranslationHistoryResponse> response = historyService.getAllHistoriesByMember(member);
        return ResponseEntity.ok(response);
    }
}
