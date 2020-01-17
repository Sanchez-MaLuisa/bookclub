package com.example.demo.mapper;

import com.example.demo.service.book.externallibrary.googlebook.models.IndustryIdentifier;
import com.example.demo.service.book.externallibrary.googlebook.models.Item;
import com.example.demo.service.book.externallibrary.googlebook.models.VolumeInfo;
import com.example.demo.service.book.model.BookFromLibrary;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ILibraryMapper {
    BookFromLibrary GoogleBooksItemToBookFromLibraryMapper(@NotNull Item item);
    String getIsbn13FromLibrary(@NotNull List<IndustryIdentifier> identifiers);
}
