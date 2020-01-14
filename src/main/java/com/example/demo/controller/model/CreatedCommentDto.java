package com.example.demo.controller.model;

import lombok.Data;

@Data
public class CreatedCommentDto {
    private Long id;
    private String text;
    private Long reviewId;
    private Long ownerId;
}

