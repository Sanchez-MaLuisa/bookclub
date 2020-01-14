package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT t FROM Author t where t.name = :authorName")
    Optional<Author> findByName(@Param("authorName") String authorName);
}
