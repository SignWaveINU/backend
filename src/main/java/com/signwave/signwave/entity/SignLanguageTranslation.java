package com.signwave.signwave.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // JPA용 기본 생성자
@AllArgsConstructor                                 // Builder 내부에서 사용
@Builder                                            // 💡 Builder 사용 선언
public class SignLanguageTranslation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(columnDefinition = "TEXT")
    private String signLanguageInput;  // 수어 입력 데이터 (예: JSON 등)

    private String translatedText;

    private String translatedAudio;

    @Column(columnDefinition = "TEXT")
    private String gestureSequence;

    @OneToMany(mappedBy = "signLanguageTranslation")
    private List<TranslationHistory> histories;
}
