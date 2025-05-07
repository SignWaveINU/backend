// TranslationHistoryController.java
package com.signwave.signwave.controller;

import com.signwave.signwave.dto.FavoriteRequest;
import com.signwave.signwave.dto.FavoriteResponse;
import com.signwave.signwave.service.TranslationHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class TranslationHistoryController {

    private final TranslationHistoryService historyService;

    @PostMapping("/favorite")
    @Operation(summary = "즐겨찾기 등록", description = "특정 번역기록을 즐겨찾기로 등록합니다.")
    public ResponseEntity<FavoriteResponse> addFavorite(@RequestBody FavoriteRequest request) {
        FavoriteResponse response = historyService.markAsFavorite(request);
        return ResponseEntity.ok(response);
    }
}
