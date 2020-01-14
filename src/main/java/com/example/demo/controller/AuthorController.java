package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Author;
import com.example.demo.persistence.entity.Book;
import com.example.demo.service.IAuthorService;
import com.example.demo.service.book.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {
    private IAuthorService authorService;
    private IBookService bookService;
    @Autowired
    public AuthorController(IAuthorService authorService, IBookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/authors/{id}")
    public Author getAuthorsById(@PathVariable(value="id") Long authorId) throws ResourceNotFoundException {
        return authorService.getAuthorsById(authorId);
    }

    @GetMapping("/authors/{id}/books")
    public List<Book> getBooksFromAuthor(@PathVariable(value="id") Long authorId) throws ResourceNotFoundException {
        Author author = authorService.getAuthorsById(authorId);
        return bookService.getBooksByAuthor(author);
    }

    @PostMapping("/authors")
    public Author createAuthor(@Valid @RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @DeleteMapping("/authors/{id}")
    public Map<String, Boolean>  deleteAuthor(@PathVariable(value="id") Long authorId) throws ResourceNotFoundException {
        Author author = authorService.getAuthorsById(authorId);
        authorService.deleteAuthor(author);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);;
        return response;
    }
}
