package com.kodeleku.springBootTensorflowImageGenerate.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String prompt;
    
    @Column(nullable = false)
    private String imageUrl;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(length = 1000)
    private String metadata;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
} 