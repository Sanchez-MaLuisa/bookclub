package com.example.demo.controller.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthorDto {
    @NotNull
    private String name;
}
