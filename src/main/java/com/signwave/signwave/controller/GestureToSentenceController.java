package com.signwave.signwave.controller;

import com.signwave.signwave.dto.GestureSequenceRequest;
import com.signwave.signwave.dto.SentenceResponse;
import com.signwave.signwave.service.GestureToSentenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/gesture-to-sentence")
@RequiredArgsConstructor
public class GestureToSentenceController {

    private final GestureToSentenceService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "CSV → 자연어 문장 변환 (Gemini)",
            description = "CSV 파일로 제스처 데이터를 업로드하면, Gemini를 통해 자연어 문장으로 변환합니다.")
    public ResponseEntity<SentenceResponse> convertToSentenceOnly(
            @Parameter(description = "제스처 CSV 파일", required = true)
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        File tempFile = File.createTempFile("gesture-", ".csv");
        file.transferTo(tempFile);

        String sentence = service.convertCsvToSentence(tempFile);
        tempFile.delete();

        return ResponseEntity.ok(new SentenceResponse(sentence));
    }
}
