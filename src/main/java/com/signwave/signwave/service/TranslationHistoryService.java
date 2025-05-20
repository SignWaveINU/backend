package com.signwave.signwave.service;

import com.signwave.signwave.dto.FavoriteRequest;
import com.signwave.signwave.dto.FavoriteResponse;
import com.signwave.signwave.dto.TranslationHistoryResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.entity.TranslationHistory;
import com.signwave.signwave.repository.TranslationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * 특정 회원의 번역기록 전체를 조회하여 응답 DTO 리스트로 반환
     *
     * @param member 현재 로그인된 사용자
     * @return 해당 사용자의 번역기록 리스트 (TranslationHistoryResponse)
     */
    @Transactional(readOnly = true)
    public List<TranslationHistoryResponse> getAllHistoriesByMember(Member member) {
        return historyRepository.findByMember(member).stream()
                .map(TranslationHistoryResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TranslationHistoryResponse> getFavoriteHistoriesByMember(Member member) {
        return historyRepository.findByMemberAndIsFavoriteTrueOrderByCreatedTimeDesc(member).stream()
                .map(TranslationHistoryResponse::fromEntity)
                .toList();
    }

    @Transactional
    public void deleteHistory(Long historyId, Member member) {
        TranslationHistory history = historyRepository.findById(historyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 번역기록이 존재하지 않습니다."));

        if (!history.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("해당 번역기록을 삭제할 권한이 없습니다.");
        }

        historyRepository.delete(history);
    }

    @Transactional
    public FavoriteResponse unmarkFavorite(Long historyId) {
        TranslationHistory history = historyRepository.findById(historyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 번역기록이 존재하지 않습니다."));

        history.setFavorite(false); // 즐겨찾기 해제
        TranslationHistory updated = historyRepository.save(history);

        return new FavoriteResponse(updated.getMember().getId());
    }



}