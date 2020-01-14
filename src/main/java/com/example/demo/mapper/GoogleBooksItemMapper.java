package com.example.demo.mapper;

import com.example.demo.service.book.externallibrary.googlebook.models.IndustryIdentifier;
import com.example.demo.service.book.externallibrary.googlebook.models.Item;
import com.example.demo.service.book.externallibrary.googlebook.models.VolumeInfo;
import com.example.demo.service.book.model.BookFromLibrary;

import java.util.List;

public class GoogleBooksItemMapper {
    public static BookFromLibrary GoogleBooksItemToBookFromLibraryMapper(Item item) {

        VolumeInfo info = item.getVolumeInfo();
        BookFromLibrary book = new BookFromLibrary();
        book.setIsbn_13(getIsbn13FromLibrary(info.getIndustryIdentifiers()));
        book.setAuthor(info.getAuthors().get(0));
        book.setTitle(info.getTitle());

        return book;
    }

    private static String getIsbn13FromLibrary(List<IndustryIdentifier> identifiers) {
        String isbn_13 = null;
        for(int i = 0; i < identifiers.size(); i++) {
            if(identifiers.get(i).getType().equals("ISBN_13")) {
                isbn_13 = identifiers.get(i).getIdentifier();
            }
        }
        return isbn_13;
    }

}
