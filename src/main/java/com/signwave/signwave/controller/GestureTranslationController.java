package com.signwave.signwave.controller;

import com.signwave.signwave.dto.GestureSequenceRequest;
import com.signwave.signwave.dto.GestureTranslationResponse;
import com.signwave.signwave.service.GestureTranslationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/translate")
@RequiredArgsConstructor
@Tag(name = "Gesture Translation", description = "제스처 시퀀스를 문장으로 변환하는 API")
public class GestureTranslationController {

    private final GestureTranslationService translationService;

    @PostMapping
    @Operation(summary = "제스처 → 자연어 문장 변환", description = "제스처 시퀀스를 입력받아 FastAPI로 전송 후 자연어 문장과 음성(mp3) URL을 반환합니다.")
    public ResponseEntity<GestureTranslationResponse> translate(
            @RequestBody GestureSequenceRequest request
    ) {
        // 문장 + audioUrl 포함된 응답 받기
        GestureTranslationResponse response = translationService.getTranslatedSentence(request.getSequence());
        return ResponseEntity.ok(response);
    }
}
