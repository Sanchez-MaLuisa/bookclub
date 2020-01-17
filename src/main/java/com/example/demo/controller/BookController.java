package com.example.demo.controller;

import com.example.demo.controller.model.CreatedBookDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.mapper.IBookMapper;
import com.example.demo.persistence.entity.Book;
import com.example.demo.service.author.IAuthorService;
import com.example.demo.service.book.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    private IBookService bookService;
    private IAuthorService authorService;
    private IBookMapper bookMapper;
    @Autowired
    public BookController(IBookService bookService, IAuthorService authorService, IBookMapper bookMapper) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.bookMapper = bookMapper;
    }

    @GetMapping("/books")
    public List<CreatedBookDto> listAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return bookMapper.bookModelListToCreatedBookDtoList(books);
    }

    @GetMapping("/books/{id}")
    public CreatedBookDto getBookById(@PathVariable(value="id") Long bookId) throws ResourceNotFoundException {
        Book book = bookService.getBookById(bookId);
        return bookMapper.bookModelToCreatedBookDto(book);
    }
}
