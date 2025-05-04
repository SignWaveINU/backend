package com.signwave.signwave.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GestureTranslationResponse {

    @Schema(description = "자연어로 변환된 문장", example = "병원에 가고 싶어요")
    private String sentence;
}