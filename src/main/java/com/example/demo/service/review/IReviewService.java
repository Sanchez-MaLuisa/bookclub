package com.example.demo.service.review;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;

import java.util.List;

public interface IReviewService {
    List<Review> getAllReviews();
    Review getReviewById(Long reviewId) throws ResourceNotFoundException;
    Boolean checkValidOwnerOfReview(Owner owner, Long reviewId) throws ResourceNotFoundException, Exception;
    Review saveReview(Review review);
    void deleteReview(Review review);
    List<Review> getPopularByAuthorId(Long authorId);
    List<Review> getReviewByAuthorId(Long authorId);
}
