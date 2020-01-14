package com.example.demo.mapper;

import com.example.demo.controller.model.CreatedReviewDto;
import com.example.demo.controller.model.OwnerDto;
import com.example.demo.controller.model.ReviewDto;
import com.example.demo.persistence.entity.Book;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;

import java.util.ArrayList;
import java.util.List;

public class CreateReviewMapper {
    public static Review reviewDtoToReviewModel(Owner owner, Book book, ReviewDto dto) {
        Review review = new Review();
        review.setTitle(dto.getTitle());
        review.setText(dto.getText());
        review.setBook(book);
        review.setComments(new ArrayList<>());
        review.setOwner(owner);
        return review;
    }

    public static CreatedReviewDto reviewModelToCreatedReviewDto(Review review) {
        CreatedReviewDto dto = new CreatedReviewDto();
        dto.setId(review.getId());
        dto.setTitle(review.getTitle());
        dto.setText(review.getText());
        dto.setBookId(review.getBook().getId());
        dto.setOwnerId(review.getOwner().getId());

        List<Long> commentsId = new ArrayList<>();
        for(int i = 0; i < review.getComments().size(); i++) {
            commentsId.add(review.getComments().get(i).getId());
        }
        dto.setCommentsId(commentsId);

        return dto;
    }
}
