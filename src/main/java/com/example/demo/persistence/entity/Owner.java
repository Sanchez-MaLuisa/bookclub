package com.example.demo.persistence.entity;

import com.example.demo.constraint.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @ValidPassword
    private String password;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Review> reviews;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "owner")
    private List<Comment> comments;

    public Owner() {}
}