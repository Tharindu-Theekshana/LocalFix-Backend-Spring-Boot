package com.localfix.localfix.repository;

import com.localfix.localfix.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepo extends JpaRepository <Review, Integer> {
    List<Review> findByProfileId(int id);

    List<Review> findByCustomerId(int id);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.profile.id = :profileId")
    Double findAverageRatingByProfileId(@Param("profileId") int profileId);
}
