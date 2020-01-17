package com.example.demo.mapper;

import com.example.demo.controller.model.AuthorDto;
import com.example.demo.controller.model.CreatedAuthorDto;
import com.example.demo.persistence.entity.Author;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public interface IAuthorMapper {
    Author authorDtoToAuthorModel(@NotNull AuthorDto authorDto);
    CreatedAuthorDto authorModelToCreatedAuthorDto(@NotNull Author author);
    List<CreatedAuthorDto> authorModelListToCreatedAuthorDtoList(@NotNull List<Author> authors);
}
