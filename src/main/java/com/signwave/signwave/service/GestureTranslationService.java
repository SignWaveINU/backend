package com.signwave.signwave.service;

import com.signwave.signwave.dto.GestureTranslationResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.entity.SignLanguageTranslation;
import com.signwave.signwave.entity.TranslationHistory;
import com.signwave.signwave.repository.MemberRepository;
import com.signwave.signwave.repository.SignLanguageTranslationRepository;
import com.signwave.signwave.repository.TranslationHistoryRepository;
import com.signwave.signwave.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GestureTranslationService {

    private final RestTemplate restTemplate;
    private final SignLanguageTranslationRepository translationRepo;
    private final TranslationHistoryRepository historyRepo;
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader; // ✅ S3에 업로드하기 위한 유틸 클래스 주입

    @Value("${ai.url}")
    private String aiUrl; // ✅ FastAPI 서버 주소

    /**
     * 현재 로그인된 사용자의 이메일을 바탕으로 제스처 시퀀스를 번역
     * 번역 결과를 저장하고, 자연어 문장과 음성 URL을 포함한 응답 반환
     */
    public GestureTranslationResponse getTranslatedSentence(List<List<Float>> sequence) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member) authentication.getPrincipal(); // ✅ principal에서 직접 꺼냄

        return translateAndSave(sequence, member);
    }

    /**
     * FastAPI에 제스처 시퀀스를 전달하고, 반환된 문장 및 음성(mp3)을 처리하여 저장
     * S3에 업로드한 URL을 포함한 응답을 구성하여 반환
     */
    public GestureTranslationResponse translateAndSave(List<List<Float>> sequence, Member member) {
        // 🔹 FastAPI 요청 바디 구성
        Map<String, Object> body = Map.of("sequence", sequence);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(body, headers);

        // 🔹 FastAPI 호출 (문장 + 음성 base64 반환)
        ResponseEntity<GestureTranslationResponse> response = restTemplate.postForEntity(
                aiUrl + "/predict_gesture_and_translate",
                request,
                GestureTranslationResponse.class
        );

        String sentence = response.getBody().getSentence();
        String audioBase64 = response.getBody().getAudioUrl();

        // 🔹 mp3 파일을 S3에 업로드하고 URL 획득
        String filename = "tts/" + UUID.randomUUID() + ".mp3";
        String s3Url = s3Uploader.uploadBase64Audio(audioBase64, filename);

        // 🔹 번역 결과 저장 (문장 + 음성 URL)
        SignLanguageTranslation translation = SignLanguageTranslation.builder()
                .member(member)
                .translatedText(sentence)
                .audioUrl(s3Url)
                .signLanguageInput("시퀀스 생략")
                .gestureSequence("제스처 생략")
                .build();
        translationRepo.save(translation);

        // 🔹 번역 기록 저장
        TranslationHistory history = TranslationHistory.builder()
                .signLanguageTranslation(translation)
                .member(member)
                .isFavorite(false)
                .build();
        historyRepo.save(history);

        // 🔹 최종 응답 구성
        GestureTranslationResponse result = new GestureTranslationResponse();
        result.setSentence(sentence);
        result.setAudioUrl(s3Url); // 실제로는 audioUrl이지만 기존 필드 재사용
        return result;
    }
}
