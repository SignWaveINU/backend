package com.signwave.signwave.service;

import com.signwave.signwave.dto.FavoriteRequest;
import com.signwave.signwave.dto.FavoriteResponse;
import com.signwave.signwave.entity.TranslationHistory;
import com.signwave.signwave.repository.TranslationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationHistoryService {

    private final TranslationHistoryRepository historyRepository;

    public FavoriteResponse markAsFavorite(FavoriteRequest request) {
        TranslationHistory history = historyRepository.findById(request.getTranslationHistoryId())
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 번역기록이 존재하지 않습니다."));

        history.setFavorite(true); // true로 설정
        TranslationHistory updated = historyRepository.save(history);

        return new FavoriteResponse(updated.getMember().getId());
    }
}