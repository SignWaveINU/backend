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

        }
    }
}
