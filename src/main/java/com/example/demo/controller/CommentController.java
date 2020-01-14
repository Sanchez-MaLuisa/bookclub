package com.example.demo.controller;

import com.example.demo.controller.model.CommentDto;
import com.example.demo.controller.model.ReviewDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Book;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import com.example.demo.service.ICommentService;
import com.example.demo.service.IReviewService;
import com.example.demo.service.book.IBookService;
import com.example.demo.service.book.externallibrary.ILibraryService;
import com.example.demo.service.book.model.BookFromLibrary;
import com.example.demo.service.owner.IOwnerService;
import com.example.demo.service.owner.security.IOwnerSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    private ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public List<Comment> listAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/comments/{id}")
    public Comment getCommentById(@PathVariable(value = "id") Long commentId)
            throws ResourceNotFoundException {
        return commentService.getCommentById(commentId);
    }
    //get comment from review
    //get comments of Owner
}