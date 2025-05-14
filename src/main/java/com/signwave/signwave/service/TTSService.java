package com.signwave.signwave.service;

import com.signwave.signwave.dto.GestureTranslationResponse;
import com.signwave.signwave.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TTSService {

    private final RestTemplate restTemplate;
    private final S3Uploader s3Uploader;

    @Value("${ai.url}")
    private String aiUrl;

    public GestureTranslationResponse generate(String sentence) {
        Map<String, String> body = Map.of("sentence", sentence);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<GestureTranslationResponse> response = restTemplate.postForEntity(
                aiUrl + "/generate_tts",
                request,
                GestureTranslationResponse.class
        );

        // S3 업로드
        String base64 = response.getBody().getAudioUrl();
        String filename = "tts/" + UUID.randomUUID() + ".mp3";
        String s3Url = s3Uploader.uploadBase64Audio(base64, filename);

        GestureTranslationResponse result = new GestureTranslationResponse();
        result.setSentence(sentence);
        result.setAudioUrl(s3Url);

        return result;
    }
}