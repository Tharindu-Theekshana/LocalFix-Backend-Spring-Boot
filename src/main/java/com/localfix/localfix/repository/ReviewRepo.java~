package com.localfix.localfix.repository;

import com.localfix.localfix.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository <Review, Integer> {
    List<Review> findByProfileId(int id);

    List<Review> findByCustomerId(int id);
}
