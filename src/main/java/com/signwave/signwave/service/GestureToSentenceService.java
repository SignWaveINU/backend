package com.signwave.signwave.service;

import com.signwave.signwave.dto.SentenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GestureToSentenceService {

    @Value("${ai.url}")
    private String aiUrl;

    private final RestTemplate restTemplate;

    public SentenceResponse convert(List<List<Float>> sequence) {
        Map<String, Object> body = Map.of("sequence", sequence);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(body, headers);

        ResponseEntity<SentenceResponse> response = restTemplate.postForEntity(
                aiUrl + "/predict_gesture_and_sentence",
                request,
                SentenceResponse.class
        );

        return response.getBody();
    }
}