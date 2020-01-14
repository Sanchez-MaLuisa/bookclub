package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.Book;
import com.example.demo.persistence.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    //@Query("SELECT b FROM Book b WHERE b.isbn13 = :isbn13")
    //Optional<Book> findByIsbn13(@Param("isbn13") String isbn13);
    //findByOwner
    //findByReview
}
