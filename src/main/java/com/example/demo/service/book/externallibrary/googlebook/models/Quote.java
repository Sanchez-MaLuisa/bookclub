package com.example.demo.service.book.externallibrary.googlebook.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    private Long totalItems;
    private List<Item> items;

    public Quote() {}
}
