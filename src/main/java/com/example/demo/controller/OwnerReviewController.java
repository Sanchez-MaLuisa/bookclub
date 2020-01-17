package com.example.demo.controller;

import com.example.demo.controller.model.CreatedReviewDto;
import com.example.demo.controller.model.ReviewDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.IReviewMapper;
import com.example.demo.persistence.entity.Book;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import com.example.demo.service.review.IReviewService;
import com.example.demo.service.book.IBookService;
import com.example.demo.service.book.externallibrary.ILibraryService;
import com.example.demo.service.book.model.BookFromLibrary;
import com.example.demo.service.owner.IOwnerService;
import com.example.demo.mapper.ReviewMapper;
import com.example.demo.service.owner.security.IOwnerSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OwnerReviewController {
    private IOwnerService ownerService;
    private IReviewService reviewService;
    private ILibraryService libraryService;
    private IBookService bookService;
    private IOwnerSecurityService ownerSecurityService;
    private IReviewMapper reviewMapper;
    @Autowired
    public OwnerReviewController(IOwnerService ownerService, IReviewService reviewService,
                                 ILibraryService libraryService, IBookService bookService,
                                 IOwnerSecurityService ownerSecurityService, IReviewMapper reviewMapper) {
        this.ownerService = ownerService;
        this.reviewService = reviewService;
        this.libraryService = libraryService;
        this.bookService = bookService;
        this.ownerSecurityService = ownerSecurityService;
        this.reviewMapper = reviewMapper;
    }

    @GetMapping("/owners/{id}/reviews")
    public List<CreatedReviewDto> getReviewsFromOwner(@RequestHeader(value = "Authorization") String token,
                                                      @PathVariable(value="id") Long ownerId)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerById(ownerId);
        ownerSecurityService.checkIfValidToken(owner, token);
        List<Review> reviews = owner.getReviews();
        return reviewMapper.reviewModelListToCreatedReviewDtoList(reviews);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/owners/{id}/reviews")
    public CreatedReviewDto createReviewInOwner(@RequestHeader(value = "Authorization") String token,
                                                @PathVariable(value="id") Long ownerId,
                                                @Valid @RequestBody ReviewDto reviewInput)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerById(ownerId);
        ownerSecurityService.checkIfValidToken(owner, token);

        BookFromLibrary bookFromLibrary = libraryService.getBookByIsbn13(reviewInput.getBookIsbn13());
        Book book = bookService.saveBookFromLibrary(bookFromLibrary);

        Review review = reviewMapper.reviewDtoToReviewModel(owner, book, reviewInput);
        Review savedReview = reviewService.saveReview(review);

        return reviewMapper.reviewModelToCreatedReviewDto(savedReview);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/owners/{ownerId}/reviews/{reviewId}")
    public CreatedReviewDto deleteReviewFromOwner(@RequestHeader(value = "Authorization") String token,
                                                  @PathVariable(value="ownerId") Long ownerId,
                                                  @PathVariable(value="reviewId") Long reviewId)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerById(ownerId);
        ownerSecurityService.checkIfValidToken(owner, token);
        reviewService.checkValidOwnerOfReview(owner,reviewId);
        Review review = reviewService.getReviewById(reviewId);
        reviewService.deleteReview(review);
        return reviewMapper.reviewModelToCreatedReviewDto(review);
    }
}
