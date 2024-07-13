package com.develop.BookFinder.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "books")
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @Enumerated(EnumType.STRING)
    private List<Languages> languages;

    @Nullable
    private Integer downloadCount;


    public Book(BookDTO bookDTO) {
        this.title = bookDTO.title();
        this.authors = bookDTO.authors().stream().map(Author::new).collect(Collectors.toList());
        this.languages = bookDTO.languages();
        this.downloadCount = bookDTO.downloadCount() != null ? bookDTO.downloadCount() : 0;
    }


    @Override
    public String toString() {
        String authorsString = authors.stream().map(Author::toString).collect(Collectors.joining("\n\t"));

        String languagesString = languages.stream().map(Languages::toString).collect(Collectors.joining("\n\t"));

        return String.format("""
                |-------------------------------------------------------------------------------------|
                  Title: %s
                  Author:\s
                  \t%s
                  Languages:
                  \t%s
                  Download Count: %d
                |-------------------------------------------------------------------------------------|""",
                title,
                authorsString.replaceAll("\n", "\n| \t"),
                languagesString.replaceAll("\n", "\n| \t"),
                downloadCount);
    }
}
