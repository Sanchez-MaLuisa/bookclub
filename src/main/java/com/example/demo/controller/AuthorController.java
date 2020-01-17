package com.example.demo.controller;

import com.example.demo.controller.model.CreatedAuthorDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.AuthorMapper;
import com.example.demo.mapper.IAuthorMapper;
import com.example.demo.persistence.entity.Author;
import com.example.demo.service.author.IAuthorService;
import com.example.demo.service.book.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {
    private IAuthorService authorService;
    private IBookService bookService;
    private IAuthorMapper authorMapper;
    @Autowired
    public AuthorController(IAuthorService authorService, IBookService bookService, IAuthorMapper authorMapper) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.authorMapper = authorMapper;
    }

    @GetMapping("/authors")
    public List<CreatedAuthorDto> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        return authorMapper.authorModelListToCreatedAuthorDtoList(authors);
    }

    @GetMapping("/authors/{id}")
    public CreatedAuthorDto getAuthorsById(@PathVariable(value="id") Long authorId) throws ResourceNotFoundException {
        Author author = authorService.getAuthorsById(authorId);
        return authorMapper.authorModelToCreatedAuthorDto(author);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/authors")
    public CreatedAuthorDto createAuthor(@Valid @RequestBody Author authorInput) {
        Author author = authorService.saveAuthor(authorInput);
        return authorMapper.authorModelToCreatedAuthorDto(author);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/authors/{id}")
    public CreatedAuthorDto deleteAuthor(@PathVariable(value="id") Long authorId) throws ResourceNotFoundException {
        Author author = authorService.getAuthorsById(authorId);
        authorService.deleteAuthor(author);
        return authorMapper.authorModelToCreatedAuthorDto(author);
    }
}
