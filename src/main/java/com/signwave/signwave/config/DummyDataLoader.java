package com.signwave.signwave.config;

import com.signwave.signwave.entity.*;
import com.signwave.signwave.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DummyDataLoader implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final SignLanguageTranslationRepository translationRepository;
    private final TranslationHistoryRepository historyRepository;
    private final HospitalReservationRepository reservationRepository;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 10000; i++) {
            Member member = Member.builder()
                    .email("user" + i + "@example.com")
                    .nickname("사용자" + i)
                    .password("$2a$10$7DkQ5zM7BCXYgEJgGLOqTuP2GnKq6JgaVUY1xJYyjb4F5ghZqLVqK") // 'password' 암호화
                    .build();
            memberRepository.save(member);

            for (int j = 1; j <= 10; j++) {
                SignLanguageTranslation translation = SignLanguageTranslation.builder()
                        .member(member)
                        .translatedText("문장 예시 " + j)
                        .audioUrl("https://mock-url.com/audio" + j + ".mp3")
                        .build();
                translationRepository.save(translation);

                TranslationHistory history = TranslationHistory.builder()
                        .member(member)
                        .signLanguageTranslation(translation)
                        .isFavorite(j % 3 == 0) // 3개 중 하나만 즐겨찾기로
                        .build();
                historyRepository.save(history);
            }

            for (int k = 1; k <= 2; k++) {
                HospitalReservation reservation = HospitalReservation.builder()
                        .member(member)
                        .hospitalName("가상병원 " + k)
                        .reservationDate(LocalDate.now().plusDays(k))
                        .reservationTime(LocalDateTime.now().plusHours(k))
                        .build();
                reservationRepository.save(reservation);
            }
        }
    }
}
