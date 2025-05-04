package com.signwave.signwave.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자 보호
@AllArgsConstructor                                 // 빌더가 사용할 전체 생성자
@Builder                                            // 💡 Builder 사용 선언
@Table(name = "translation_history")
public class TranslationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sign_language_translation_id", nullable = false)
    private SignLanguageTranslation signLanguageTranslation;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private boolean isFavorite;
}
