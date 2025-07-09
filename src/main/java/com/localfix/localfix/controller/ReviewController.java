package com.localfix.localfix.controller;


import com.localfix.localfix.dto.Response;
import com.localfix.localfix.dto.ReviewDto;
import com.localfix.localfix.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;


    @PostMapping("/submitReview")
    public ResponseEntity<Response> submitReview(@RequestHeader("Authorization") String token, @RequestBody ReviewDto reviewDto){

        Response response = reviewService.submitReview(token,reviewDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/displayReviewsOfEachProfile/{id}")
    public ResponseEntity<List<ReviewDto>> displayReview(@PathVariable int id){
        return reviewService.displayReview(id);
    }

    @GetMapping("/displayReviewsOfEachCustomer/{id}")
    public ResponseEntity<List<ReviewDto>> displayReviewsOfCustomer(@RequestHeader("Authorization") String token,@PathVariable int id){
        return reviewService.displayReviewsOfCustomer(token,id);
    }

    @PutMapping("/editReview/{id}")
    public ResponseEntity<Response> editReview(@RequestHeader("Authorization") String token,@RequestBody ReviewDto reviewDto,@PathVariable int id){

        Response response = reviewService.editReview(token,reviewDto,id);
        return ResponseEntity.ok(response);
    }
}
