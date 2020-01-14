package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.Review;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT t FROM Review t where t.book.id = :bookId")
    Optional<Review> findByBookId(@Param("bookId") Long bookId);

    @Query("SELECT t FROM Review t where t.book.author.id = :authorId")
    Optional<Review> findByAuthorId(@Param("authorId") Long authorId);

    @Query("SELECT t FROM Review t where t.book.author.id = :authorId order by t.comments.size desc ")
    List<Review> findPopularByAuthorId(@Param("authorId") Long authorId);
}
