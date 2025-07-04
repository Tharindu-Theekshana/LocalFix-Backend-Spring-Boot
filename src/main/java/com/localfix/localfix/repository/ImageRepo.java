package com.localfix.localfix.repository;

import com.localfix.localfix.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image, Integer> {
}
