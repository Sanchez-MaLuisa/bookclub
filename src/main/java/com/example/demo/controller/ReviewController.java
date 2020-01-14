package com.example.demo.controller;

import com.example.demo.controller.model.CreatedReviewDto;
import com.example.demo.controller.model.ReviewDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CreateReviewMapper;
import com.example.demo.persistence.entity.Review;
import com.example.demo.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {
    private IReviewService reviewService;

    @Autowired
    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public List<CreatedReviewDto> getOwnersReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        List<CreatedReviewDto> reviewDtos = new ArrayList<>();
        for(int i = 0; i < reviews.size(); i++) {
            reviewDtos.add(CreateReviewMapper.reviewModelToCreatedReviewDto(reviews.get(i)));
        }
        return reviewDtos;
    }

    @GetMapping("/reviews/{id}")
    public CreatedReviewDto getReviewById(@PathVariable(value="id") Long reviewId) throws ResourceNotFoundException {
        Review review = reviewService.getReviewById(reviewId);
        return CreateReviewMapper.reviewModelToCreatedReviewDto(review);
    }

    @GetMapping("/reviews/{id}/suggestion")
    public List<CreatedReviewDto> getPopularSuggestionById(@PathVariable(value="id") Long reviewId) throws ResourceNotFoundException {
        Review review = reviewService.getReviewById(reviewId);
        List<Review> reviews = reviewService.getPopularByAuthorId(review.getBook().getAuthor().getId());
        List<CreatedReviewDto> reviewDtos = new ArrayList<>();
        for(int i = 0; i < reviews.size(); i++) {
            reviewDtos.add(CreateReviewMapper.reviewModelToCreatedReviewDto(reviews.get(i)));
        }
        return reviewDtos;
    }
}
