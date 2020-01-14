package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.Author;
import com.example.demo.persistence.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOwnerRepository extends JpaRepository<Owner, Long>{
    @Query("SELECT t FROM Owner t where t.username = :username")
    Optional<Owner> findByUsername(@Param("username") String username);
}

