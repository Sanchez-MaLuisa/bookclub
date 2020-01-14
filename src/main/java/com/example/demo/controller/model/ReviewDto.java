package com.example.demo.controller.model;

import com.example.demo.persistence.entity.Book;
import com.example.demo.persistence.entity.Comment;
import lombok.Data;

import java.util.List;

@Data
public class ReviewDto {
    private String title;
    private String bookIsbn13;
    private String text;
    private Long ownerId;
}
