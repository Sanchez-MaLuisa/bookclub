package com.example.demo.mapper;

import com.example.demo.controller.model.CreatedReviewDto;
import com.example.demo.controller.model.ReviewDto;
import com.example.demo.persistence.entity.Book;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public interface IReviewMapper {
    Review reviewDtoToReviewModel(@NotNull Owner owner, @NotNull Book book, @NotNull ReviewDto dto);
    CreatedReviewDto reviewModelToCreatedReviewDto(@NotNull Review review);
    List<CreatedReviewDto> reviewModelListToCreatedReviewDtoList(@NotNull List<Review> reviews);
}
