package com.signwave.signwave.controller;

import com.signwave.signwave.dto.LoginRequest;
import com.signwave.signwave.dto.LoginResponse;
import com.signwave.signwave.dto.SignUpRequest;
import com.signwave.signwave.dto.SignUpResponse;
import com.signwave.signwave.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "회원가입 및 인증 관련 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "이메일, 닉네임, 비밀번호로 새로운 사용자를 등록합니다.")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest request) {
        SignUpResponse response = memberService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 반환합니다.")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = memberService.login(request);
        return ResponseEntity.ok(response);
    }

}
