package com.example.demo.service.book;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Author;
import com.example.demo.persistence.entity.Book;
import com.example.demo.service.book.model.BookFromLibrary;

import java.util.List;

public interface IBookService {
    List<Book> getAllBooks();
    List<Book> getBooksByAuthor(Author author);
    Book getBookByIsbn13(String isbn13) throws ResourceNotFoundException;
    Book getBookById(Long bookId) throws ResourceNotFoundException;
    Book saveBookFromLibrary(BookFromLibrary bookFromLibrary);
    void deleteBook(Book book);
}
