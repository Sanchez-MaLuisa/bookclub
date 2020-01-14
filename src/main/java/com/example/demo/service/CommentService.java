package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import com.example.demo.persistence.repository.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {
    private ICommentRepository commentRepository;
    @Autowired
    public CommentService(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(Long commentId) throws ResourceNotFoundException {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment not found by Id: " + commentId));
        return comment;
    }

    @Override
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Boolean checkValidOwnerForComment(Owner owner, Long commentId) throws ResourceNotFoundException, Exception {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found by Id: " + commentId));
        if(comment.getOwner().getId() != owner.getId())
            throw new Exception("Dont have permission to modify this comment: " + commentId);

        return true;
    }

    @Override
    public Boolean checkValidReviewForComment(Review review, Long commentId) throws ResourceNotFoundException, Exception {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found by Id: " + commentId));
        if(comment.getReview().getId() != review.getId())
            throw new Exception("This comment doesnot belong to this review: " + commentId);
        return true;
    }
}
