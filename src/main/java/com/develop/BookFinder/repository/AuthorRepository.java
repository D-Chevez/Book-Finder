package com.develop.BookFinder.repository;

import com.develop.BookFinder.model.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long>{
    List<Author> findByNameContainingIgnoreCase(String name);

    //@Query("SELECT DISTINCT a FROM Author a WHERE a.deathYear <= :determinedYear")
    //List<Author> findAuthorsLivingUntilYear(Integer determinedYear);
}
