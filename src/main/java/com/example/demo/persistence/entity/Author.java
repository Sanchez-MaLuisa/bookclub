package com.example.demo.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(nullable = false)
    private String name;

    public Author() {}
}
