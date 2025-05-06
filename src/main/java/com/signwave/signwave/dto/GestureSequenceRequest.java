package com.signwave.signwave.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class GestureSequenceRequest {

    @Schema(
            description = "제스처 시퀀스 (프레임당 126개 특징 벡터)",
            example = "[[0.1, 0.2, 0.3, 0.4], [0.5, 0.6, 0.7, 0.8]]"
    )
    private List<List<Float>> sequence;
}