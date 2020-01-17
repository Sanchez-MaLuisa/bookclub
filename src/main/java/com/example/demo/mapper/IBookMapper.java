package com.example.demo.mapper;

import com.example.demo.controller.model.CreatedBookDto;
import com.example.demo.persistence.entity.Book;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public interface IBookMapper {
    CreatedBookDto bookModelToCreatedBookDto(@NotNull Book book);
    List<CreatedBookDto> bookModelListToCreatedBookDtoList(@NotNull List<Book> books);
}
