package com.signwave.signwave.controller;

import com.signwave.signwave.dto.GestureSequenceRequest;
import com.signwave.signwave.dto.SentenceResponse;
import com.signwave.signwave.service.GestureToSentenceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gesture-to-sentence")
@RequiredArgsConstructor
public class GestureToSentenceController {

    private final GestureToSentenceService service;

    @PostMapping
    @Operation(summary = "제스처 → 문장 변환", description = "제스처 시퀀스를 자연어 문장으로 변환합니다.")
    public ResponseEntity<SentenceResponse> convertToSentence(@RequestBody GestureSequenceRequest request) {
        SentenceResponse response = service.convert(request.getSequence());
        return ResponseEntity.ok(response);
    }
}
