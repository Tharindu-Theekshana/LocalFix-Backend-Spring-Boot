package com.localfix.localfix.service;

import com.localfix.localfix.dto.Response;
import com.localfix.localfix.dto.ReviewDto;
import com.localfix.localfix.model.Profile;
import com.localfix.localfix.model.Review;
import com.localfix.localfix.model.User;
import com.localfix.localfix.repository.ProfileRepo;
import com.localfix.localfix.repository.ReviewRepo;
import com.localfix.localfix.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    @Autowired
    TokenBlackList tokenBlackList;

    @Autowired
    ReviewRepo reviewRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ProfileRepo profileRepo;

    public Response submitReview(String token, ReviewDto reviewDto) {

        if (!tokenBlackList.isTokenBlacklisted(token)) {

            try{
                User customer = userRepo.findById(reviewDto.getCustomerId()).orElseThrow(()-> new RuntimeException("No customer found!"));
                Profile profile = profileRepo.findById(reviewDto.getProfileId()).orElseThrow(()-> new RuntimeException("No profile found!"));

                Review review = new Review();
                review.setComment(reviewDto.getComment());
                review.setRating(reviewDto.getRating());
                review.setCustomer(customer);
                review.setProfile(profile);
                reviewRepo.save(review);

                return new Response("Review submitted successfully!",true);
            }catch (Exception e){
                return new Response("cant submit review!", false);
            }

        }else {
            return new Response("You must login first!", false);
        }
    }

    public ResponseEntity<List<ReviewDto>> displayReview(int id) {

        try{

            List<Review> reviews = reviewRepo.findByProfileId(id);
            List<ReviewDto> reviewDtos = reviews.stream().map(review -> {

                ReviewDto reviewDto = new ReviewDto();
                reviewDto.setId(review.getId());
                reviewDto.setComment(review.getComment());
                reviewDto.setRating(review.getRating());
                reviewDto.setCustomerId(review.getCustomer().getId());
                reviewDto.setProfileId(review.getProfile().getId());
                reviewDto.setCustomerEmail(review.getCustomer().getEmail());

                return reviewDto;
            }).toList();

            return ResponseEntity.ok(reviewDtos);

        } catch (RuntimeException e) {
            throw new RuntimeException("cant display reviews : " + e.getMessage());
        }
    }
}
