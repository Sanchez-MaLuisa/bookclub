package com.example.demo.service.book.externallibrary.googlebook;

import com.example.demo.service.book.externallibrary.ILibraryService;
import com.example.demo.service.book.externallibrary.googlebook.models.IndustryIdentifier;
import com.example.demo.service.book.externallibrary.googlebook.models.Item;
import com.example.demo.service.book.externallibrary.googlebook.models.Quote;
import com.example.demo.service.book.externallibrary.googlebook.models.VolumeInfo;
import com.example.demo.service.book.model.BookFromLibrary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
public class GoogleBookService implements ILibraryService {
    private RestTemplate restTemplate;
    private String key;
    private String baseUrl;

    public GoogleBookService() {
        this.restTemplate = new RestTemplate();
        this.key = "AIzaSyDGSB0625Jyebcy2oZ49CVhXFF9sFl1ZC8";
        this.baseUrl = "https://www.googleapis.com/books/v1/volumes?q=";
    }

    private Item getItemInfoByIsbn13(String isbn) {
        String url = baseUrl + "isbn:" + isbn +
                "&key=" + key;
        Quote quote = restTemplate.getForObject(url, Quote.class);
        return quote.getItems().get(0);
    }

    private List<Item> searchItems(String title, String author) {
        Boolean empty = true;
        String url = baseUrl;
        if(title != "") {
            url += title;
            empty = false;
        }
        if(author != "") {
            if(!empty)
                url += "+";
            url += "inauthor:" + author;
        }
        url += "&key=" + key;

        Quote quote = restTemplate.getForObject(url, Quote.class);
        List<Item> items = quote.getItems();
        return items;
    }


    @Override
    public BookFromLibrary getBookByIsbn13(String isbn_13) {
        BookFromLibrary book = new BookFromLibrary();
        Item item = getItemInfoByIsbn13(isbn_13);
        book.setIsbn_13(isbn_13);
        book.setTitle(item.getVolumeInfo().getTitle());
        book.setAuthor(item.getVolumeInfo().getAuthors().get(0));
        return book;
    }

    @Override
    public List<BookFromLibrary> searchBooks(String title, String author) {
        List<Item> items = searchItems(title, author);
        List<BookFromLibrary> books = new ArrayList<>();

        if(items != null)
        {
            for(int i = 0; i < items.size(); i++) {
                BookFromLibrary book = GoogleBooksItemToBookFromLibraryMapper( items.get(i) );
                books.add(book);
            }
        }
        return books;
    }

    private BookFromLibrary GoogleBooksItemToBookFromLibraryMapper(Item item) {
        VolumeInfo info = item.getVolumeInfo();
        BookFromLibrary book = new BookFromLibrary();

        if(info.getIndustryIdentifiers() == null)
            book.setIsbn_13(null);
        else
            book.setIsbn_13(getIsbn13FromLibrary(info.getIndustryIdentifiers()));

        if(info.getAuthors() == null)
            book.setAuthor(null);
        else
            book.setAuthor(info.getAuthors().get(0));

        if(info.getTitle() == null)
            book.setTitle(null);
        else
            book.setTitle(info.getTitle());

        return book;
    }

    private String getIsbn13FromLibrary(List<IndustryIdentifier> identifiers) {
        String isbn_13 = null;
        for(int i = 0; i < identifiers.size(); i++) {
            if(identifiers.get(i).getType().equals("ISBN_13")) {
                isbn_13 = identifiers.get(i).getIdentifier();
            }
        }
        return isbn_13;
    }
}
