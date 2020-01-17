package com.example.demo.controller;

import com.example.demo.controller.model.CreatedBookDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.IBookMapper;
import com.example.demo.persistence.entity.Author;
import com.example.demo.persistence.entity.Book;
import com.example.demo.service.author.IAuthorService;
import com.example.demo.service.book.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthorBookController {
    private IAuthorService authorService;
    private IBookService bookService;
    private IBookMapper bookMapper;

    @Autowired
    public AuthorBookController(IAuthorService authorService, IBookService bookService, IBookMapper bookMapper) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.bookMapper  = bookMapper;
    }

    @GetMapping("/authors/{id}/books")
    public List<CreatedBookDto> getBooksFromAuthor(@PathVariable(value="id") Long authorId) throws ResourceNotFoundException {
        Author author = authorService.getAuthorsById(authorId);
        List<Book> books = bookService.getBooksByAuthor(author);
        return bookMapper.bookModelListToCreatedBookDtoList(books);
    }
}
