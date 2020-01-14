package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Book;
import com.example.demo.service.IAuthorService;
import com.example.demo.service.book.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    private IBookService bookService;
    private IAuthorService authorService;
    @Autowired
    public BookController(IBookService bookService, IAuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/books")
    public List<Book> listAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable(value="id") Long bookId) throws ResourceNotFoundException {
        return bookService.getBookById(bookId);
    }
}
