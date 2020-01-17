package com.example.demo.controller.model;

import lombok.Data;

import java.util.List;

@Data
public class CreatedAuthorDto {
    private Long id;
    private String name;
    private List<Long> booksId;
}
