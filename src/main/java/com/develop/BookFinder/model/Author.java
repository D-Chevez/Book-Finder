package com.develop.BookFinder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name= "authors")
@Table (name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer birthYear;

    private Integer deathYear;

    @OneToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private List<Book> books;


    public Author(AuthorDTO authorDTO) {
        this.name = authorDTO.name();
        this.birthYear = authorDTO.birthYear() != null && authorDTO.birthYear().describeConstable().isPresent() ? authorDTO.birthYear() : 0;
        this.deathYear = authorDTO.deathYear() != null && authorDTO.deathYear().describeConstable().isPresent() ? authorDTO.deathYear() : 0;
    }

    @Override
    public String toString() {
        return String.format("%s (Born: %d, Died: %d)", name, birthYear, deathYear);
    }

}
