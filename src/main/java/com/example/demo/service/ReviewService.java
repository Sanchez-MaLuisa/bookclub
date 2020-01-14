package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Author;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import com.example.demo.persistence.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService implements IReviewService {
    private IReviewRepository reviewRepository;
    @Autowired
    public ReviewService(IReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getReviewById(Long reviewId) throws ResourceNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found by Id: " + reviewId));
        return review;
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Review review) {
        reviewRepository.delete(review);
    }

    @Override
    public Boolean checkValidOwnerOfReview(Owner owner, Long reviewId)
            throws ResourceNotFoundException, Exception {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found by Id: " + reviewId));
        if(review.getOwner().getId() != owner.getId())
            throw new Exception("Dont have permission to modify this review: " + reviewId);
        return true;
    }

    @Override
    public List<Review> getPopularByAuthorId(Long authorId) {
        List<Review> reviews = reviewRepository.findPopularByAuthorId(authorId);
        return reviews;
    }
}
