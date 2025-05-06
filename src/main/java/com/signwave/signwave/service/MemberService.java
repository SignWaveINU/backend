package com.signwave.signwave.service;

import com.signwave.signwave.dto.SignUpRequest;
import com.signwave.signwave.dto.SignUpResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse register(SignUpRequest request) {
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        Member member = Member.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .password(encryptedPassword)
                .build();

        Member saved = memberRepository.save(member);
        return new SignUpResponse(saved.getId(), saved.getEmail(), saved.getNickname());
    }
}
