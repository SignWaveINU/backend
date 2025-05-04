package com.signwave.signwave.repository;

import com.signwave.signwave.entity.SignLanguageTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignLanguageTranslationRepository extends JpaRepository<SignLanguageTranslation, Long> {
}
