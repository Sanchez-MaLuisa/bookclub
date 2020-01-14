package com.example.demo.controller.model;

import com.example.demo.constraint.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class OwnerDto {
    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @ValidPassword
    private String password;
}
