package com.signwave.signwave.controller;

import com.signwave.signwave.dto.GestureSequenceRequest;
import com.signwave.signwave.dto.GestureTranslationResponse;
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
    @Operation(summary = "제스처 → 자연어 문장 변환", description = "제스처 시퀀스를 입력받아 FastAPI로 전송 후 자연어 문장을 반환합니다.")
    public ResponseEntity<GestureTranslationResponse> translate(
            @RequestBody GestureSequenceRequest request
    ) {
        // FastAPI 호출 결과 문장 얻기
        String sentence = translationService.getTranslatedSentence(request.getSequence());

        // 응답 객체 구성 후 반환
        GestureTranslationResponse response = new GestureTranslationResponse();
        response.setSentence(sentence);
        return ResponseEntity.ok(response);
    }
}