package com.signwave.signwave.dto;

import com.signwave.signwave.entity.TranslationHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TranslationHistoryResponse {

    private Long translationHistoryId;
    private String translatedText;
    private String audioUrl;
    private boolean isFavorite;
    private LocalDateTime createdTime; // 수정: createdAt → createdTime

    public static TranslationHistoryResponse fromEntity(TranslationHistory history) {
        return TranslationHistoryResponse.builder()
                .translationHistoryId(history.getId())
                .translatedText(history.getSignLanguageTranslation().getTranslatedText())
                .audioUrl(history.getSignLanguageTranslation().getAudioUrl()) // ✅ S3 URL 주입
                .isFavorite(history.isFavorite())
                .createdTime(history.getCreatedTime()) // 수정: getCreatedAt() → getCreatedTime()
                .build();
    }
}

