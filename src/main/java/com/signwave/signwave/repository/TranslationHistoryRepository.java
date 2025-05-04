package com.signwave.signwave.repository;

import com.signwave.signwave.entity.TranslationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationHistoryRepository extends JpaRepository<TranslationHistory, Long> {
}
