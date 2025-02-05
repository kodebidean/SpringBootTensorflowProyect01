package com.kodeleku.springBootTensorflowImageGenerate.controller;

import com.kodeleku.springBootTensorflowImageGenerate.model.Image;
import com.kodeleku.springBootTensorflowImageGenerate.model.User;
import com.kodeleku.springBootTensorflowImageGenerate.service.CloudinaryService;
import com.kodeleku.springBootTensorflowImageGenerate.service.ImageService;
import com.kodeleku.springBootTensorflowImageGenerate.service.TensorFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final TensorFlowService tensorFlowService;
    private final CloudinaryService cloudinaryService;
    
    @GetMapping
    public ResponseEntity<List<Image>> getUserImages(@RequestAttribute User user) {
        return ResponseEntity.ok(imageService.getUserImages(user));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Image> getImage(@PathVariable Long id, @RequestAttribute User user) {
        return imageService.getImage(id, user)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Image> generateImage(@RequestBody String prompt, @RequestAttribute User user) {
        byte[] generatedImage = tensorFlowService.generateImage(prompt);
        String imageUrl = cloudinaryService.uploadImage(generatedImage);
        
        Image savedImage = imageService.saveImage(
            prompt,
            imageUrl,
            user,
            "{}");
        return ResponseEntity.ok(savedImage);
    }
} 