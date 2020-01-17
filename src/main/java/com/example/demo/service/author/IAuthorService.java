package com.example.demo.service.author;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Author;
import java.util.List;

public interface IAuthorService {
    List<Author> getAllAuthors();
    Author getAuthorsByName(String name) throws ResourceNotFoundException;
    Author getAuthorsById(Long authorId) throws ResourceNotFoundException;
    Author saveAuthor(Author author);
    void deleteAuthor(Author author);
}
