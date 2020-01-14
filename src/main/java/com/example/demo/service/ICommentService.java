package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Book;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;

import java.util.List;

public interface ICommentService {
    List<Comment> getAllComments();
    Comment getCommentById(Long genreId) throws ResourceNotFoundException;
    Comment saveComment(Comment comment);
    void deleteComment(Comment comment);
    Boolean checkValidOwnerForComment(Owner owner, Long commentId) throws ResourceNotFoundException, Exception;
    Boolean checkValidReviewForComment(Review review, Long commentId) throws ResourceNotFoundException, Exception;
}
