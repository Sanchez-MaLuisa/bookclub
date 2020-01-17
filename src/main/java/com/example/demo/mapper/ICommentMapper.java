package com.example.demo.mapper;

import com.example.demo.controller.model.CommentDto;
import com.example.demo.controller.model.CreatedCommentDto;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public interface ICommentMapper {
    Comment commentDtoToCommentModel(@NotNull CommentDto dto, @NotNull Owner owner, @NotNull Review review);
    CreatedCommentDto commentModelToCreatedCommentDto(@NotNull Comment comment);
    List<CreatedCommentDto> commentModelListToCreatedCommentDtoList(@NotNull List<Comment> comments);
}
