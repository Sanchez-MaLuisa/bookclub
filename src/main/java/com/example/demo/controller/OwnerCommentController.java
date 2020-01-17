package com.example.demo.controller;

import com.example.demo.controller.model.CreatedCommentDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.ICommentMapper;
import com.example.demo.persistence.entity.Comment;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.service.comment.ICommentService;
import com.example.demo.service.owner.IOwnerService;
import com.example.demo.service.owner.security.IOwnerSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OwnerCommentController {
    private IOwnerService ownerService;
    private ICommentService commentService;
    private IOwnerSecurityService ownerSecurityService;
    private ICommentMapper commentMapper;
    @Autowired
    public OwnerCommentController(IOwnerService ownerService, ICommentService commentService,
                                  IOwnerSecurityService ownerSecurityService, ICommentMapper commentMapper) {
        this.ownerService = ownerService;
        this.commentService = commentService;
        this.ownerSecurityService = ownerSecurityService;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/owners/{id}/comments")
    public List<CreatedCommentDto> getAllCommentsFromOwner(@PathVariable(value="id") Long ownerId)
            throws ResourceNotFoundException {
        Owner owner = ownerService.getOwnerById(ownerId);
        List<CreatedCommentDto> commentDtos = commentMapper.commentModelListToCreatedCommentDtoList(owner.getComments());
        return commentDtos;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/owners/{ownerId}/comments/{commentId}")
    public CreatedCommentDto deleteCommentOfOwner( @RequestHeader(value = "Authorization") String token,
                                                   @PathVariable(value="ownerId") Long ownerId,
                                                   @PathVariable(value="commentId") Long commentId)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerById(ownerId);
        ownerSecurityService.checkIfValidToken(owner, token);
        commentService.checkValidOwnerForComment(owner,commentId);

        Comment comment = commentService.getCommentById(commentId);
        commentService.deleteComment(comment);

        return commentMapper.commentModelToCreatedCommentDto(comment);
    }
}
