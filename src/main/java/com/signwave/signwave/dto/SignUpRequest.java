package com.signwave.signwave.dto;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String email;
    private String nickname;
    private String password;
}