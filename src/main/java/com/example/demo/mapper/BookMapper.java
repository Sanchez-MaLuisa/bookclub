package com.example.demo.mapper;

import com.example.demo.controller.model.CreatedBookDto;
import com.example.demo.persistence.entity.Book;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookMapper implements IBookMapper{
    @Override
    public CreatedBookDto bookModelToCreatedBookDto(@NotNull Book book) {
        CreatedBookDto bookDto = new CreatedBookDto();
        bookDto.setId(book.getId());
        bookDto.setIsbn13(book.getIsbn13());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthorId((book.getAuthor() == null) ? null : book.getAuthor().getId());
        return bookDto;
    }

    @Override
    public List<CreatedBookDto> bookModelListToCreatedBookDtoList(@NotNull List<Book> books) {
        List<CreatedBookDto> bookDtos = new ArrayList<>();
        for(int i = 0; i < books.size(); i++) {
            bookDtos.add(bookModelToCreatedBookDto(books.get(i)));
        }
        return bookDtos;
    }
}
