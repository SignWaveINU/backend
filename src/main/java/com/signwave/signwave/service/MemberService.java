package com.signwave.signwave.service;

import com.signwave.signwave.dto.LoginRequest;
import com.signwave.signwave.dto.LoginResponse;
import com.signwave.signwave.dto.SignUpRequest;
import com.signwave.signwave.dto.SignUpResponse;
import com.signwave.signwave.entity.Member;
import com.signwave.signwave.jwt.JwtTokenProvider;
import com.signwave.signwave.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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

    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.generateToken(member.getEmail());
        return new LoginResponse(token);
    }
}
