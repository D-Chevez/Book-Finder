package com.develop.BookFinder.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(
        @JsonAlias ("title") String title,
        @JsonAlias ("authors") List<AuthorDTO> authors,
        @JsonAlias ("languages") List<Languages> languages,
        @JsonAlias ("download_count") Integer downloadCount) {
}