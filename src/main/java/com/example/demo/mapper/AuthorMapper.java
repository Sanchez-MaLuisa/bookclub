package com.example.demo.mapper;

import com.example.demo.controller.model.AuthorDto;
import com.example.demo.controller.model.CreatedAuthorDto;
import com.example.demo.persistence.entity.Author;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorMapper implements IAuthorMapper{
    @Override
    public Author authorDtoToAuthorModel(@NotNull AuthorDto authorDto) {
        Author author = new Author();
        author.setName(authorDto.getName());
        author.setBooks(new ArrayList<>());
        return author;
    }

    @Override
    public CreatedAuthorDto authorModelToCreatedAuthorDto(@NotNull Author author) {
        CreatedAuthorDto authorDto = new CreatedAuthorDto();
        authorDto.setName(author.getName());
        List<Long> bookIds = new ArrayList<>();
        for(int i = 0; i < author.getBooks().size(); i++) {
            bookIds.add(author.getBooks().get(i).getId());
        }
        authorDto.setBooksId(bookIds);
        return authorDto;
    }

    @Override
    public List<CreatedAuthorDto> authorModelListToCreatedAuthorDtoList(@NotNull List<Author> authors) {
        List<CreatedAuthorDto> authorDtos = new ArrayList<>();
        for(int i = 0; i < authors.size(); i++) {
            authorDtos.add(authorModelToCreatedAuthorDto(authors.get(i)));
        }
        return authorDtos;
    }
}
