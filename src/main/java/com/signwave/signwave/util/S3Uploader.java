package com.signwave.signwave.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadBase64Audio(String base64Audio, String filename) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Audio);
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("audio/mpeg");
            metadata.setContentLength(decodedBytes.length);

            amazonS3.putObject(bucket, filename, inputStream, metadata);

            return amazonS3.getUrl(bucket, filename).toString();
        } catch (Exception e) {
            throw new RuntimeException("S3 mp3 업로드 실패", e);
        }
    }
}
