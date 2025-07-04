package com.localfix.localfix.repository;

import com.localfix.localfix.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository <Review, Integer> {
}
