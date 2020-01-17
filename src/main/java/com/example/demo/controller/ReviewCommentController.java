package com.example.demo.controller;

import com.example.demo.controller.model.CommentDto;
import com.example.demo.controller.model.CreatedCommentDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.ICommentMapper;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import com.example.demo.service.comment.ICommentService;
import com.example.demo.service.review.IReviewService;
import com.example.demo.service.owner.IOwnerService;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.service.owner.security.IOwnerSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ReviewCommentController {
    private IOwnerService ownerService;
    private ICommentService commentService;
    private IReviewService reviewService;
    private IOwnerSecurityService ownerSecurityService;
    private ICommentMapper commentMapper;
    @Autowired
    public ReviewCommentController(IOwnerService ownerService, ICommentService commentService,
                                   IReviewService reviewService, IOwnerSecurityService ownerSecurityService,
                                   ICommentMapper commentMapper) {
        this.commentService = commentService;
        this.ownerService = ownerService;
        this.reviewService = reviewService;
        this.ownerSecurityService = ownerSecurityService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/reviews/{id}/comments")
    public List<CreatedCommentDto> getAllCommentsFromReview(@PathVariable(value="id") Long reviewId)
            throws ResourceNotFoundException {
        Review review = reviewService.getReviewById(reviewId);
        return commentMapper.commentModelListToCreatedCommentDtoList(review.getComments());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reviews/{id}/comments")
    public CreatedCommentDto createCommentInReview(@RequestParam Long ownerId,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   @PathVariable(value="id") Long reviewId,
                                                   @Valid @RequestBody CommentDto commentInput)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerById(ownerId);
        ownerSecurityService.checkIfValidToken(owner, token);
        Review review = reviewService.getReviewById(reviewId);

        Comment comment = commentMapper.commentDtoToCommentModel(commentInput, owner, review);
        Comment savedComment = commentService.saveComment(comment);
        return commentMapper.commentModelToCreatedCommentDto(savedComment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/reviews/{reviewId}/comments/{commentId}")
    public CreatedCommentDto deleteCommentInReview(@RequestParam Long ownerId,
                                                   @RequestHeader(value = "Authorization") String token,
                                                   @PathVariable(value="reviewId") Long reviewId,
                                                   @PathVariable(value="commentId") Long commentId)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerById(ownerId);
        ownerSecurityService.checkIfValidToken(owner, token);
        Review review = reviewService.getReviewById(reviewId);

        commentService.checkValidOwnerForComment(owner,commentId);
        commentService.checkValidReviewForComment(review, commentId);

        Comment comment = commentService.getCommentById(commentId);
        commentService.deleteComment(comment);
        return commentMapper.commentModelToCreatedCommentDto(comment);
    }
}
