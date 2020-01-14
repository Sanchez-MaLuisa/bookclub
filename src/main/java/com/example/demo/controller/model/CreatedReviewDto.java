package com.example.demo.controller.model;

import com.example.demo.persistence.entity.Review;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreatedReviewDto {
    private Long id;
    private String title;
    private Long bookId;
    private String text;
    private Long ownerId;
    private List<Long> commentsId;
}
