package com.example.demo.mapper;

import com.example.demo.controller.model.CommentDto;
import com.example.demo.controller.model.CreatedCommentDto;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentMapper implements ICommentMapper{
    @Override
    public Comment commentDtoToCommentModel(@NotNull CommentDto dto, @NotNull Owner owner, @NotNull Review review) {
        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setOwner(owner);
        comment.setReview(review);
        return comment;
    }

    @Override
    public CreatedCommentDto commentModelToCreatedCommentDto(@NotNull Comment comment) {
        CreatedCommentDto dto = new CreatedCommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setOwnerId((comment.getOwner() == null) ? null : comment.getOwner().getId());
        dto.setReviewId((comment.getReview() == null) ? null : comment.getReview().getId());
        return dto;
    }

    @Override
    public List<CreatedCommentDto> commentModelListToCreatedCommentDtoList(@NotNull List<Comment> comments) {
        List<CreatedCommentDto> commentDtos = new ArrayList<>();
        for(int i = 0; i < comments.size(); i++) {
            commentDtos.add(commentModelToCreatedCommentDto(comments.get(i)));
        }
        return commentDtos;
    }
}