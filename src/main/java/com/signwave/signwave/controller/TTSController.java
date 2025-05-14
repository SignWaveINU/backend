package com.signwave.signwave.controller;

import com.signwave.signwave.dto.SentenceRequest;
import com.signwave.signwave.dto.GestureTranslationResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.service.TTSService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tts")
@RequiredArgsConstructor
public class TTSController {

    private final TTSService service;

    @PostMapping
    @Operation(summary = "문장 → TTS 변환", description = "문장을 입력받아 TTS(mp3 base64)를 생성하고 S3 URL을 반환합니다.")
    public ResponseEntity<GestureTranslationResponse> generateTTS(@RequestBody SentenceRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) auth.getPrincipal();

        return ResponseEntity.ok(service.generate(request.getSentence(), member));
    }
}
