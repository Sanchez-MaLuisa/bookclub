package com.example.demo.controller;

import com.example.demo.controller.model.CommentDto;
import com.example.demo.controller.model.CreatedCommentDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import com.example.demo.service.ICommentService;
import com.example.demo.service.IReviewService;
import com.example.demo.service.owner.IOwnerService;
import com.example.demo.mapper.CreateCommentMapper;
import com.example.demo.service.owner.security.IOwnerSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ReviewCommentController {
    private IOwnerService ownerService;
    private ICommentService commentService;
    private IReviewService reviewService;
    private IOwnerSecurityService ownerSecurityService;
    @Autowired
    public ReviewCommentController(IOwnerService ownerService, ICommentService commentService,
                                   IReviewService reviewService, IOwnerSecurityService ownerSecurityService) {
        this.commentService = commentService;
        this.ownerService = ownerService;
        this.reviewService = reviewService;
        this.ownerSecurityService = ownerSecurityService;
    }

    @GetMapping("/reviews/{id}/comments")
    public List<CreatedCommentDto> getAllCommentsFromReview(@PathVariable(value="id") Long reviewId)
            throws ResourceNotFoundException {
        Review review = reviewService.getReviewById(reviewId);

        List<CreatedCommentDto> comments = new ArrayList<>();
        for(int i = 0; i < review.getComments().size(); i++) {
            comments.add(CreateCommentMapper.commentModelToCreatedCommentDto(review.getComments().get(i)));
        }
        return comments;
    }

    @PostMapping("/reviews/{id}/comments")
    public Map<String, Boolean> createCommentInReview(@RequestParam Long ownerId,
                                                    @RequestParam String token,
                                                    @PathVariable(value="id") Long reviewId,
                                                    @Valid @RequestBody CommentDto commentInput)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerById(ownerId);
        ownerSecurityService.checkIfValidToken(owner, token);
        Review review = reviewService.getReviewById(reviewId);

        Comment comment = CreateCommentMapper.commentDtoToCommentModel(commentInput, owner, review);

        review.getComments().add(comment);
        reviewService.saveReview(review);

        Map<String, Boolean> response = new HashMap<>();
        response.put("review created", Boolean.TRUE);;
        return response;
    }

    @DeleteMapping("/reviews/{reviewId}/comments/{commentId}")
    public Map<String, Boolean> deleteReviewInComment(@RequestParam Long ownerId,
                                                      @RequestParam String token,
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

        Map<String, Boolean> response = new HashMap<>();
        response.put("comment deleted", Boolean.TRUE);;
        return response;
    }
}
