package com.example.demo.controller.model;
import lombok.Data;

import java.util.List;

@Data
public class CreatedOwnerDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private List<Long> reviewsId;
    private List<Long> commentsId;
}
