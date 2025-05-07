package com.signwave.signwave.dto;

import com.signwave.signwave.entity.TranslationHistory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TranslationHistoryResponse {

    private Long id;
    private String translatedText;
    private boolean isFavorite;

    public static TranslationHistoryResponse fromEntity(TranslationHistory history) {
        return TranslationHistoryResponse.builder()
                .id(history.getId())
                .translatedText(history.getSignLanguageTranslation().getTranslatedText())
                .isFavorite(history.isFavorite())
                .build();
    }
}
