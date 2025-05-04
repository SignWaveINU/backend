package com.signwave.signwave.service;

import com.signwave.signwave.dto.GestureTranslationResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.entity.SignLanguageTranslation;
import com.signwave.signwave.entity.TranslationHistory;
import com.signwave.signwave.repository.SignLanguageTranslationRepository;
import com.signwave.signwave.repository.TranslationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GestureTranslationService {

    private final RestTemplate restTemplate;
    private final SignLanguageTranslationRepository translationRepo;
    private final TranslationHistoryRepository historyRepo;

    // application.yml에 정의된 FastAPI 서버 주소 주입
    @Value("${ai.url}")
    private String aiUrl;

    /**
     * 제스처 시퀀스를 FastAPI에 전달하여 문장으로 번역하고,
     * 번역 결과를 DB에 저장한 후 문장을 반환한다.
     *
     * @param sequence 제스처 시퀀스 데이터
     * @param member   현재 로그인한 사용자
     * @return 번역된 자연어 문장
     */
    public String translateAndSave(List<List<Float>> sequence, Member member) {

        // 1. FastAPI에 보낼 요청 바디 구성
        Map<String, Object> body = Map.of("sequence", sequence);

        // 2. HTTP 요청 헤더 설정 (Content-Type: application/json)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 3. 요청 엔티티 구성 (바디 + 헤더)
        HttpEntity<?> request = new HttpEntity<>(body, headers);

        // 4. FastAPI의 /predict_gesture_and_translate 엔드포인트 호출
        ResponseEntity<GestureTranslationResponse> response = restTemplate.postForEntity(
                aiUrl + "/predict_gesture_and_translate",
                request,
                GestureTranslationResponse.class
        );

        // 5. FastAPI로부터 반환받은 문장 추출
        String sentence = response.getBody().getSentence();

        // 6. 수어 번역 결과를 DB에 저장 (SignLanguageTranslation)
        SignLanguageTranslation translation = SignLanguageTranslation.builder()
                .member(member)                        // 현재 로그인한 사용자
                .translatedText(sentence)              // 번역된 문장
                .signLanguageInput("시퀀스 생략")      // 추후: 실제 시퀀스를 문자열로 저장 가능
                .gestureSequence("제스처 생략")         // 추후: 제스처 라벨 시퀀스 저장 가능
                .translatedAudio(null)                 // 음성 미생성 시 null
                .build();

        translationRepo.save(translation);             // 저장

        // 7. 번역 기록 히스토리도 함께 저장 (TranslationHistory)
        TranslationHistory history = TranslationHistory.builder()
                .signLanguageTranslation(translation)  // 위에서 저장한 번역 엔티티 참조
                .member(member)                        // 사용자
                .isFavorite(false)                     // 기본값: 즐겨찾기 아님
                .build();

        historyRepo.save(history);                     // 저장

        // 8. 최종 결과 문장을 컨트롤러로 반환
        return sentence;
    }

    public String getTranslatedSentence(List<List<Float>> sequence) {
        return translateAndSave(sequence, null);
    }
}
