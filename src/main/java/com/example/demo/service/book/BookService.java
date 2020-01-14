package com.example.demo.service.book;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Author;
import com.example.demo.persistence.entity.Book;
import com.example.demo.persistence.repository.IAuthorRepository;
import com.example.demo.persistence.repository.IBookRepository;
import com.example.demo.service.book.model.BookFromLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {
    private IBookRepository bookRepository;
    private IAuthorRepository authorRepository;
    @Autowired
    public BookService(IBookRepository bookRepository, IAuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByAuthor(Author author) {
        return bookRepository.findByAuthorId(author.getId());
    }

    @Override
    public Book getBookById(Long bookId)
            throws ResourceNotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found by Id: " + bookId));
        return book;
    }

    @Override
    public Book getBookByIsbn13(String isbn13)
            throws ResourceNotFoundException {
        Book book = bookRepository.findByIsbn13(isbn13)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found by ISBN: " + isbn13));
        return book;
    }

    @Override
    public Book saveBook(BookFromLibrary bookFromLibrary) {
        Book book = new Book();
        Author author = new Author();
        Optional<Book> bookInDb = bookRepository.findByIsbn13(bookFromLibrary.getIsbn_13());
        if(!bookInDb.isPresent()) {
            Optional<Author> authorInDb = authorRepository.findByName(bookFromLibrary.getAuthor());
            if(!authorInDb.isPresent()) {
                author.setName(bookFromLibrary.getAuthor());
                author = authorRepository.save(author);
            }
            else {
                author = authorInDb.get();
            }
            book.setTitle(bookFromLibrary.getTitle());
            book.setIsbn13(bookFromLibrary.getIsbn_13());
            book.setAuthor(author);
        }
        else {
            book = bookInDb.get();
        }
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }
}
