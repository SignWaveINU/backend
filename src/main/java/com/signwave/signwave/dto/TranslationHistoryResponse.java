package com.signwave.signwave.dto;

import com.signwave.signwave.entity.TranslationHistory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TranslationHistoryResponse {

    private Long translationHistoryId;
    private String translatedText;
    private boolean isFavorite;

    public static TranslationHistoryResponse fromEntity(TranslationHistory history) {
        return TranslationHistoryResponse.builder()
                .translationHistoryId(history.getId())
                .translatedText(history.getSignLanguageTranslation().getTranslatedText())
                .isFavorite(history.isFavorite())
                .build();
    }
}
