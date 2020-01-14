package com.example.demo.controller;

import com.example.demo.service.book.externallibrary.ILibraryService;
import com.example.demo.service.book.model.BookFromLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LibraryController {
    private ILibraryService libraryService;

    @Autowired
    public LibraryController(ILibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/library/")
    public List<BookFromLibrary> getBooks(@RequestParam String title, @RequestParam String author) {
        List<BookFromLibrary> booksFromLibrary = libraryService.searchBooks(title, author);
        return booksFromLibrary;
    }

    @GetMapping("/library/{isbn_13}")
    public BookFromLibrary getBookByIsbn(@PathVariable(value="isbn_13") String isbn_13) {
        BookFromLibrary bookFromLibrary = libraryService.getBookByIsbn13(isbn_13);
        return bookFromLibrary;
    }
}
