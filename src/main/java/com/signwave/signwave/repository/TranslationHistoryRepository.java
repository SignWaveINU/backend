package com.signwave.signwave.repository;

import com.signwave.signwave.entity.Member;
import com.signwave.signwave.entity.TranslationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TranslationHistoryRepository extends JpaRepository<TranslationHistory, Long> {
    List<TranslationHistory> findByMember(Member member);
}
