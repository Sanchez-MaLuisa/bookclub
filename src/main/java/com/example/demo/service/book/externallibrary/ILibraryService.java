package com.example.demo.service.book.externallibrary;

import com.example.demo.service.book.model.BookFromLibrary;

import java.util.List;

public interface ILibraryService {
    BookFromLibrary getBookByIsbn13(String isbn_13);
    List<BookFromLibrary> searchBooks(String title, String author);
}
