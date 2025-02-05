package com.kodeleku.springBootTensorflowImageGenerate.service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;
    
    public String uploadImage(byte[] imageData) {
        try {
            Map result = cloudinary.uploader().upload(
                new ByteArrayInputStream(imageData),
                Map.of("resource_type", "image")
            );
            return (String) result.get("url");
        } catch (Exception e) {
            throw new RuntimeException("Error uploading image to Cloudinary", e);
        }
    }
} 