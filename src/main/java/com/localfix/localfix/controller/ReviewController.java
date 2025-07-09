package com.localfix.localfix.controller;


import com.localfix.localfix.dto.Response;
import com.localfix.localfix.dto.ReviewDto;
import com.localfix.localfix.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
