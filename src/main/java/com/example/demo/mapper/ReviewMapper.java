package com.example.demo.mapper;

import com.example.demo.controller.model.CreatedReviewDto;
import com.example.demo.controller.model.OwnerDto;
import com.example.demo.controller.model.ReviewDto;
import com.example.demo.persistence.entity.Book;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewMapper implements IReviewMapper{
    @Override
    public Review reviewDtoToReviewModel(@NotNull Owner owner, @NotNull Book book, @NotNull ReviewDto dto) {
        Review review = new Review();
        review.setTitle(dto.getTitle());
        review.setText(dto.getText());
        review.setComments(new ArrayList<>());
        review.setBook(book);
        review.setOwner(owner);
        return review;
    }

    @Override
    public CreatedReviewDto reviewModelToCreatedReviewDto(@NotNull Review review) {
        CreatedReviewDto dto = new CreatedReviewDto();
        dto.setId(review.getId());
        dto.setTitle(review.getTitle());
        dto.setText(review.getText());
        dto.setBookId((review.getBook() == null) ? null : review.getBook().getId());
        dto.setOwnerId((review.getOwner() == null) ? null : review.getOwner().getId());
        List<Long> commentsId = new ArrayList<>();
        for(int i = 0; i < review.getComments().size(); i++) {
            commentsId.add(review.getComments().get(i).getId());
        }
        dto.setCommentsId(commentsId);
        return dto;
    }

    @Override
    public List<CreatedReviewDto> reviewModelListToCreatedReviewDtoList(@NotNull List<Review> reviews) {
        List<CreatedReviewDto> reviewDtos = new ArrayList<>();
        for(int i = 0; i < reviews.size(); i++) {
            reviewDtos.add(reviewModelToCreatedReviewDto(reviews.get(i)));
        }
        return reviewDtos;
    }
}
