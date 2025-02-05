package com.kodeleku.springBootTensorflowImageGenerate.repository;

import com.kodeleku.springBootTensorflowImageGenerate.model.Image;
import com.kodeleku.springBootTensorflowImageGenerate.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByUserOrderByCreatedAtDesc(User user);
    Optional<Image> findByIdAndUser(Long id, User user);
} 