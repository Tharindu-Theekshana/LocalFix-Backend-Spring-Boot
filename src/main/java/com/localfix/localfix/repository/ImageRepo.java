package com.localfix.localfix.repository;

import com.localfix.localfix.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Integer> {
    List<Image> findByProfileId(int id);
}
