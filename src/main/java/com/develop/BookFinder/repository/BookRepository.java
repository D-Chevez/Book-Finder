package com.develop.BookFinder.repository;

import com.develop.BookFinder.model.Book;

import com.develop.BookFinder.model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>  {
    List<Book> findByTitleContainingIgnoreCase(String title);
}
