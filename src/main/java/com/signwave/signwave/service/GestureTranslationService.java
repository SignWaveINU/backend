package com.signwave.signwave.service;

import com.signwave.signwave.dto.GestureTranslationResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.entity.SignLanguageTranslation;
import com.signwave.signwave.entity.TranslationHistory;
import com.signwave.signwave.repository.MemberRepository;
import com.signwave.signwave.repository.SignLanguageTranslationRepository;
import com.signwave.signwave.repository.TranslationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GestureTranslationService {

    private final RestTemplate restTemplate;
    private final SignLanguageTranslationRepository translationRepo;
    private final TranslationHistoryRepository historyRepo;
    private final MemberRepository memberRepository;

    // FastAPI 서버 주소 (application.yml 등에서 주입)
    @Value("${ai.url}")
    private String aiUrl;

    /**
     * 제스처 시퀀스를 번역하고 DB에 저장하며, 번역 문장을 반환
     */
    public String getTranslatedSentence(List<List<Float>> sequence) {
        // 1. 더미 사용자 이메일 기준으로 찾거나
        Member member = memberRepository.findByEmail("test@dummy.com")
                .orElseGet(() -> {
                    // 2. 없으면 생성 후 저장
                    Member dummy = Member.builder()
                            .email("test@dummy.com")
                            .password("dummy")      // 보안 상 사용하지 않는 값
                            .nickname("dummy")      // ⚠ nickname은 not null + unique
                            .build();
                    return memberRepository.save(dummy);
                });

        // 3. 번역 및 저장
        return translateAndSave(sequence, member);
    }

    /**
     * FastAPI에 제스처를 전달해 번역 결과를 받아오고 DB에 저장
     */
    public String translateAndSave(List<List<Float>> sequence, Member member) {
        // 1. FastAPI 요청 바디
        Map<String, Object> body = Map.of("sequence", sequence);

        // 2. HTTP 요청 헤더
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3. 요청 엔티티 구성
        HttpEntity<?> request = new HttpEntity<>(body, headers);

        // 4. FastAPI 호출
        ResponseEntity<GestureTranslationResponse> response = restTemplate.postForEntity(
                aiUrl + "/predict_gesture_and_translate",
                request,
                GestureTranslationResponse.class
        );

        // 5. 결과 문장 추출
        String sentence = response.getBody().getSentence();

        // 6. SignLanguageTranslation 저장
        SignLanguageTranslation translation = SignLanguageTranslation.builder()
                .member(member)
                .translatedText(sentence)
                .signLanguageInput("시퀀스 생략")
                .gestureSequence("제스처 생략")
                .translatedAudio(null)
                .build();

        translationRepo.save(translation);

        // 7. TranslationHistory 저장
        TranslationHistory history = TranslationHistory.builder()
                .signLanguageTranslation(translation)
                .member(member)
                .isFavorite(false)
                .build();

        historyRepo.save(history);

        // 8. 최종 번역 문장 반환
        return sentence;
    }
}
