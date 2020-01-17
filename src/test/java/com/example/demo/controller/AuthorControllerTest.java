package com.example.demo.controller;

import com.example.demo.controller.model.AuthorDto;
import com.example.demo.controller.model.CreatedAuthorDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.AuthorMapper;
import com.example.demo.persistence.entity.Author;
import com.example.demo.persistence.entity.Book;
import com.example.demo.service.author.AuthorService;
import com.example.demo.service.book.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorControllerTest {
    @Mock
    AuthorService authorServiceMock;
    @Mock
    BookService bookServiceMock;
    @Mock
    AuthorMapper authorMapperMock;

    AuthorController authorController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        authorController = new AuthorController(authorServiceMock, bookServiceMock, authorMapperMock);
    }

    public static Object[][] authorTestData() {
        List<Long> booksIdList = new ArrayList<>();
        booksIdList.add((long) 1);

        Author author1 = new Author();
        author1.setId((long) 1);
        author1.setName("authorName1");
        author1.setBooks(new ArrayList<>());

        Book book = new Book();
        book.setId((long) 1);
        book.setTitle("bookTitle1");
        book.setAuthor(author1);
        book.setIsbn13("isbn13");

        List<Book> books = new ArrayList<>();
        books.add(book);
        author1.setBooks(books);

        List<Author> authorList = new ArrayList<>();
        authorList.add(author1);

        CreatedAuthorDto createdAuthorDto1 = new CreatedAuthorDto();
        createdAuthorDto1.setBooksId(booksIdList);
        createdAuthorDto1.setName("authorName1");
        createdAuthorDto1.setId((long) 1);

        List<CreatedAuthorDto> createdAuthorDtoList = new ArrayList<>();
        createdAuthorDtoList.add(createdAuthorDto1);

        return new Object[][]{
                {(long) 1, createdAuthorDto1, author1, authorList, createdAuthorDtoList}
        };
    }

    @ParameterizedTest
    @MethodSource("authorTestData")
    public void getAllAuthors_ReturnsCreatedAuthorList(
            Long authorId, CreatedAuthorDto expectedDto, Author author,
            List<Author> authors, List<CreatedAuthorDto> expectedCreatedAuthorDtos) {

        when(authorServiceMock.getAllAuthors()).thenReturn(authors);
        when(authorMapperMock.authorModelListToCreatedAuthorDtoList(authors)).thenReturn(expectedCreatedAuthorDtos);

        List<CreatedAuthorDto> createdAuthorDtos = authorController.getAllAuthors();
        assertEquals(expectedCreatedAuthorDtos, createdAuthorDtos);
    }

    @ParameterizedTest
    @MethodSource("authorTestData")
    public void getAuthorsById_ValidId_ReturnsCreatedAuthor(
            Long authorId, CreatedAuthorDto expectedDto, Author author,
            List<Author> authors, List<CreatedAuthorDto> createdAuthorDtoList)
            throws ResourceNotFoundException {

        when(authorServiceMock.getAuthorsById(authorId)).thenReturn(author);
        when(authorMapperMock.authorModelToCreatedAuthorDto(author)).thenReturn(expectedDto);

        CreatedAuthorDto authorDto = authorController.getAuthorsById(authorId);
        assertEquals(expectedDto, authorDto);
    }

    @Test
    public void getAuthorsById_InvalidId_ThrowsResourceNotFoundException()
            throws ResourceNotFoundException {
        Long authorId = (long) 1;
        when(authorServiceMock.getAuthorsById(authorId)).thenThrow(new ResourceNotFoundException("Author Not found."));

        assertThrows(ResourceNotFoundException.class, () -> authorController.getAuthorsById(authorId));
    }

    @ParameterizedTest
    @MethodSource("authorTestData")
    public void createAuthor_ValidBody_ReturnsCreatedAuthorDto(
            Long authorId, CreatedAuthorDto createdAuthorDto, Author author,
            List<Author> authors, List<CreatedAuthorDto> createdAuthorDtoList) {
        Author authorInput = new Author();
        authorInput.setName(author.getName());
        authorInput.setBooks(author.getBooks());

        when(authorServiceMock.saveAuthor(authorInput)).thenReturn(author);
        when(authorMapperMock.authorModelToCreatedAuthorDto(author)).thenReturn(createdAuthorDto);

        CreatedAuthorDto expectedCreatedAuthorDto = authorController.createAuthor(authorInput);
        assertEquals(expectedCreatedAuthorDto, createdAuthorDto);
    }

    @ParameterizedTest
    @MethodSource("authorTestData")
    public void deleteAuthor_ValidId_ReturnsCreatedAuthorDto(
            Long authorId, CreatedAuthorDto createdAuthorDto, Author author,
            List<Author> authors, List<CreatedAuthorDto> createdAuthorDtoList)
            throws ResourceNotFoundException {

        when(authorServiceMock.getAuthorsById(authorId)).thenReturn(author);
        doNothing().when(authorServiceMock).deleteAuthor(author);
        when(authorMapperMock.authorModelToCreatedAuthorDto(author)).thenReturn(createdAuthorDto);

        CreatedAuthorDto expectedCreatedAuthorDto = authorController.deleteAuthor(authorId);
        assertEquals(expectedCreatedAuthorDto, createdAuthorDto);
    }

    @Test
    public void deleteAuthor_InvalidId_ThrowsResourceNotFoundException()
            throws ResourceNotFoundException {
        Long authorId = (long) 1;
        Author author = new Author();

        when(authorServiceMock.getAuthorsById(authorId)).thenThrow(new ResourceNotFoundException("Author not found."));

        assertThrows(ResourceNotFoundException.class, () -> authorController.getAuthorsById(authorId));
    }
}