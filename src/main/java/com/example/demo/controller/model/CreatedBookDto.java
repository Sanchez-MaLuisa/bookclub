package com.example.demo.controller.model;

import com.example.demo.persistence.entity.Author;
import lombok.Data;

import javax.persistence.*;

@Data
public class CreatedBookDto {
    private long id;
    private String title;
    private String isbn13;
    private Long authorId;
}
