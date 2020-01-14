package com.example.demo.controller;

import com.example.demo.controller.model.CreatedCommentDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CreateCommentMapper;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import com.example.demo.service.ICommentService;
import com.example.demo.service.owner.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class OwnerCommentController {
    private IOwnerService ownerService;
    private ICommentService commentService;
    @Autowired
    public OwnerCommentController(IOwnerService ownerService, ICommentService commentService) {
        this.ownerService = ownerService;
        this.commentService = commentService;
    }

    @GetMapping("/owners/{id}/comments")
    public List<CreatedCommentDto> getAllCommentsFromOwner(@PathVariable(value="id") Long ownerId)
            throws ResourceNotFoundException {
        Owner owner = ownerService.getOwnerById(ownerId);
        List<CreatedCommentDto> comments = new ArrayList<>();
        for(int i = 0; i < owner.getComments().size(); i++) {
            comments.add(CreateCommentMapper.commentModelToCreatedCommentDto(owner.getComments().get(i)));
        }
        return comments;
    }

    @DeleteMapping("/owners/{ownerId}/comments/{commentId}")
    public Map<String, Boolean> deleteCommentOfOwner( @RequestParam String token,
                                                      @PathVariable(value="ownerId") Long ownerId,
                                                      @PathVariable(value="commentId") Long commentId)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerIfValid(ownerId, token);
        commentService.checkValidOwnerForComment(owner,commentId);

        Comment comment = commentService.getCommentById(commentId);
        commentService.deleteComment(comment);

        Map<String, Boolean> response = new HashMap<>();
        response.put("comment deleted", Boolean.TRUE);;
        return response;
    }
}
