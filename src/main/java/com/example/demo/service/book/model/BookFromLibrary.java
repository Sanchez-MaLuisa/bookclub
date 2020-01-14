package com.example.demo.service.book.model;

import lombok.Data;

@Data
public class BookFromLibrary {
    private String isbn_13;
    private String title;
    private String author;
}
