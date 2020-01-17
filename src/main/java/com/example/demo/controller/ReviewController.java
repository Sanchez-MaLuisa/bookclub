package com.example.demo.controller;

import com.example.demo.controller.model.CreatedReviewDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.IReviewMapper;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.persistence.entity.Review;
import com.example.demo.service.review.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewController {
    private IReviewService reviewService;
    private IReviewMapper reviewMapper;
    @Autowired
    public ReviewController(IReviewService reviewService, IReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping("/reviews")
    public List<CreatedReviewDto> getOwnersReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return reviewMapper.reviewModelListToCreatedReviewDtoList(reviews);
    }

    @GetMapping("/reviews/{id}")
    public CreatedReviewDto getReviewById(@PathVariable(value="id") Long reviewId) throws ResourceNotFoundException {
        Review review = reviewService.getReviewById(reviewId);
        return reviewMapper.reviewModelToCreatedReviewDto(review);
    }

    @GetMapping("/reviews/{id}/suggestion")
    public List<CreatedReviewDto> getPopularSuggestionById(@PathVariable(value="id") Long reviewId) throws ResourceNotFoundException {
        Review review = reviewService.getReviewById(reviewId);
        List<Review> reviews = reviewService.getPopularByAuthorId(review.getBook().getAuthor().getId());
        return reviewMapper.reviewModelListToCreatedReviewDtoList(reviews);
    }
}
