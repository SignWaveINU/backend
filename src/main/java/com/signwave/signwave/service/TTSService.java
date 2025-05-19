package com.signwave.signwave.service;

import com.signwave.signwave.dto.GestureTranslationResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.entity.SignLanguageTranslation;
import com.signwave.signwave.entity.TranslationHistory;
import com.signwave.signwave.repository.SignLanguageTranslationRepository;
import com.signwave.signwave.repository.TranslationHistoryRepository;
import com.signwave.signwave.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TTSService {

    private final RestTemplate restTemplate;
    private final S3Uploader s3Uploader;
    private final SignLanguageTranslationRepository translationRepo;
    private final TranslationHistoryRepository historyRepo;

    @Value("${ai.url}")
    private String aiUrl;

    public GestureTranslationResponse generate(String sentence, Member member) {
        // 1. FastAPI 호출 (문장 → 음성 base64)
        Map<String, String> body = Map.of("sentence", sentence);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<GestureTranslationResponse> response = restTemplate.postForEntity(
                aiUrl + "/generate_tts",
                request,
                GestureTranslationResponse.class
        );

        String base64 = response.getBody().getAudioUrl();

        // 2. base64 → S3 업로드
        String filename = "tts/" + UUID.randomUUID() + ".mp3";
        String s3Url = s3Uploader.uploadBase64Audio(base64, filename);

        // 3. DB 저장
        SignLanguageTranslation translation = SignLanguageTranslation.builder()
                .member(member)
                .translatedText(sentence)
                .audioUrl(s3Url)
                .build();
        translationRepo.save(translation);

        TranslationHistory history = TranslationHistory.builder()
                .signLanguageTranslation(translation)
                .member(member)
                .isFavorite(false)
                .build();
        historyRepo.save(history);

        // 4. 최종 응답 반환
        GestureTranslationResponse result = new GestureTranslationResponse();
        result.setSentence(sentence);
        result.setAudioUrl(s3Url);
        result.setHistoryId(history.getId());
        return result;
    }
}
