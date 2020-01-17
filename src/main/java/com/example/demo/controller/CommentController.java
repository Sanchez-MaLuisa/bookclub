package com.example.demo.controller;

import com.example.demo.controller.model.CreatedCommentDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.ICommentMapper;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.service.comment.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    private ICommentService commentService;
    private ICommentMapper commentMapper;
    @Autowired
    public CommentController(ICommentService commentService, ICommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/comments")
    public List<CreatedCommentDto> listAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return commentMapper.commentModelListToCreatedCommentDtoList(comments);
    }

    @GetMapping("/comments/{id}")
    public CreatedCommentDto getCommentById(@PathVariable(value = "id") Long commentId)
            throws ResourceNotFoundException {
        Comment comment = commentService.getCommentById(commentId);
        return commentMapper.commentModelToCreatedCommentDto(comment);
    }
}