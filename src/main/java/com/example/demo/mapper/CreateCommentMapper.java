package com.example.demo.mapper;

import com.example.demo.controller.model.CommentDto;
import com.example.demo.controller.model.CreatedCommentDto;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;


public class CreateCommentMapper {
    public static Comment commentDtoToCommentModel(CommentDto dto, Owner owner, Review review) {
        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setOwner(owner);
        comment.setReview(review);
        return comment;
    }
    public static CreatedCommentDto commentModelToCreatedCommentDto(Comment comment) {
        CreatedCommentDto dto = new CreatedCommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setOwnerId(comment.getOwner().getId());
        dto.setReviewId(comment.getReview().getId());
        return dto;
    }
}