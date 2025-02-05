package com.kodeleku.springBootTensorflowImageGenerate.service;

import com.kodeleku.springBootTensorflowImageGenerate.model.Image;
import com.kodeleku.springBootTensorflowImageGenerate.model.User;
import com.kodeleku.springBootTensorflowImageGenerate.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    
    public List<Image> getUserImages(User user) {
        return imageRepository.findByUserOrderByCreatedAtDesc(user);
    }
    
    public Optional<Image> getImage(Long id, User user) {
        return imageRepository.findByIdAndUser(id, user);
    }
    
    public Image saveImage(String prompt, String imageUrl, User user, String metadata) {
        Image image = Image.builder()
            .prompt(prompt)
            .imageUrl(imageUrl)
            .user(user)
            .metadata(metadata)
            .createdAt(LocalDateTime.now())
            .build();
        
        return imageRepository.save(image);
    }
} 