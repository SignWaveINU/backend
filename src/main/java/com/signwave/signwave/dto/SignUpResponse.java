package com.signwave.signwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponse {
    private Long id;
    private String email;
    private String nickname;
}