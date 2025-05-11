package com.signwave.signwave.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GestureTranslationResponse {

    @Schema(description = "자연어로 변환된 문장", example = "병원에 가고 싶어요")
    private String sentence;

    @Schema(description = "TTS 음성(mp3)을 base64로 인코딩한 문자열")
    private String audioBase64;

}

