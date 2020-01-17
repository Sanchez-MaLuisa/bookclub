package com.example.demo.mapper;

import com.example.demo.service.book.externallibrary.googlebook.models.IndustryIdentifier;
import com.example.demo.service.book.externallibrary.googlebook.models.Item;
import com.example.demo.service.book.externallibrary.googlebook.models.VolumeInfo;
import com.example.demo.service.book.model.BookFromLibrary;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class GoogleBooksItemMapper implements ILibraryMapper{
    @Override
    public BookFromLibrary GoogleBooksItemToBookFromLibraryMapper(@NotNull Item item) {

        VolumeInfo info = item.getVolumeInfo();
        BookFromLibrary book = new BookFromLibrary();
        book.setIsbn_13(getIsbn13FromLibrary(info.getIndustryIdentifiers()));
        book.setAuthor(info.getAuthors().get(0));
        book.setTitle(info.getTitle());

        return book;
    }

    @Override
    public String getIsbn13FromLibrary(@NotNull List<IndustryIdentifier> identifiers) {
        String isbn_13 = null;
        for(int i = 0; i < identifiers.size(); i++) {
            if(identifiers.get(i).getType().equals("ISBN_13")) {
                isbn_13 = identifiers.get(i).getIdentifier();
            }
        }
        return isbn_13;
    }
}
