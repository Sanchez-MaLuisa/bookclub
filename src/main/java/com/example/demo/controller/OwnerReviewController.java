package com.example.demo.controller;

import com.example.demo.controller.model.CreatedReviewDto;
import com.example.demo.controller.model.ReviewDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Author;
import com.example.demo.persistence.entity.Book;
import com.example.demo.persistence.entity.Owner;
import com.example.demo.persistence.entity.Review;
import com.example.demo.service.IReviewService;
import com.example.demo.service.book.IBookService;
import com.example.demo.service.book.externallibrary.ILibraryService;
import com.example.demo.service.book.model.BookFromLibrary;
import com.example.demo.service.owner.IOwnerService;
import com.example.demo.mapper.CreateReviewMapper;
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
public class OwnerReviewController {
    private IOwnerService ownerService;
    private IReviewService reviewService;
    private ILibraryService libraryService;
    private IBookService bookService;

    @Autowired
    public OwnerReviewController(IOwnerService ownerService, IReviewService reviewService,
                                 ILibraryService libraryService, IBookService bookService) {
        this.ownerService = ownerService;
        this.reviewService = reviewService;
        this.libraryService = libraryService;
        this.bookService = bookService;
    }

    @GetMapping("/owners/{id}/reviews")
    public List<CreatedReviewDto> getReviewsFromOwner(@RequestParam String token,
                                            @PathVariable(value="id") Long ownerId)
            throws ResourceNotFoundException, Exception {

        Owner owner = ownerService.getOwnerIfValid(ownerId, token);
        List<Review> reviews = owner.getReviews();

        List<CreatedReviewDto> reviewDtos = new ArrayList<>();
        for(int i = 0; i < reviews.size(); i++) {
            reviewDtos.add(CreateReviewMapper.reviewModelToCreatedReviewDto(reviews.get(i)));
        }

        return reviewDtos;
    }

    @PostMapping("/owners/{id}/reviews")
    public Map<String, Boolean> createReviewInOwner(@RequestParam String token,
                                                    @PathVariable(value="id") Long ownerId,
                                                    @Valid @RequestBody ReviewDto reviewInput)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerIfValid(ownerId, token);

        BookFromLibrary bookFromLibrary = libraryService.getBookByIsbn13(reviewInput.getBookIsbn13());
        Book book = bookService.saveBook(bookFromLibrary);

        Review review = CreateReviewMapper.reviewDtoToReviewModel(owner, book, reviewInput);

        owner.getReviews().add(review);
        ownerService.saveOwner(owner);

        Map<String, Boolean> response = new HashMap<>();
        response.put("review created", Boolean.TRUE);;
        return response;
    }

    @DeleteMapping("/owners/{ownerId}/reviews/{reviewId}")
    public Map<String, Boolean> deleteReviewFromOwner(@RequestParam String token,
                                                      @PathVariable(value="ownerId") Long ownerId,
                                                      @PathVariable(value="reviewId") Long reviewId)
            throws ResourceNotFoundException, Exception {
        Owner owner = ownerService.getOwnerIfValid(ownerId, token);
        reviewService.checkValidOwnerOfReview(owner,reviewId);
        Review review = reviewService.getReviewById(reviewId);
        reviewService.deleteReview(review);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);;
        return response;
    }
}

