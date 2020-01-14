package com.example.demo.controller;


import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.persistence.entity.Author;
import com.example.demo.service.AuthorService;
import com.example.demo.service.book.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthorControllerTest {
    @Mock
    BookService bookServiceMock;
    @Mock
    AuthorService authorServiceMock;

    AuthorController authorController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        authorController = new AuthorController(authorServiceMock, bookServiceMock);
    }

    @Test
    public void getAllAuthors_AuthorsInDb_ReturnsListOfAuthors() {
        Author author1 = new Author();
        author1.setName("author1");
        author1.setId((long) 1);
        Author author2 = new Author();
        author2.setName("author2");
        author2.setId((long) 2);

        List<Author> authorsMock = new ArrayList<>();
        authorsMock.add(author1);
        authorsMock.add(author2);

        when(authorServiceMock.getAllAuthors()).thenReturn(authorsMock);

        List<Author> authors = authorController.getAllAuthors();
        assertEquals(authors, authorsMock);
    }


    @Test
    public void getAllAuthors_NoAuthorsInDb_ReturnsEmptyList() {
        List<Author> authorsMock = new ArrayList<>();
        when(authorServiceMock.getAllAuthors()).thenReturn(authorsMock);

        List<Author> authors = authorController.getAllAuthors();

        assertEquals(authors, authorsMock);
    }

    @Test
    public void getAuthorsById_AuthorInDb_ReturnsCreatedAuthorDto() throws ResourceNotFoundException {
        Long id = (long) 1;
        Author author1 = new Author();
        author1.setName("author1");
        author1.setId(id);

        when(authorServiceMock.getAuthorsById(id)).thenReturn(author1);

        Author author = authorController.getAuthorsById(id);
        assertEquals(author, author1);
    }

    /*
    * @Test(expected = ResourceNotFoundException.class)
    public void getAuthorsById_NoAuthorInDb_ThrowsResourceNotFoundException() throws ResourceNotFoundException {
        doThrow(ResourceNotFoundException.class)
                .when(authorServiceMock)
                .getAuthorsById(anyLong());

        Author author = authorController.getAuthorsById((long) 1);
    }*/
}