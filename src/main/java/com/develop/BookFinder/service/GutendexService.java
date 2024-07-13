package com.develop.BookFinder.service;

import com.develop.BookFinder.model.*;
import com.develop.BookFinder.repository.AuthorRepository;
import com.develop.BookFinder.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class GutendexService {

    private static final String URL_BASE = "https://gutendex.com/books/";

    @Autowired
    ApiService apiService;

    @Autowired
    DataConverter dataConverter;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;


    public void getBookByTitle(String titleBook) {
        String url = URL_BASE + "?search=" + titleBook.replace(" ", "%20").toLowerCase();
        String json = apiService.getResponse(url);
        GutendexData data = dataConverter.getData(json, GutendexData.class);

        Optional<BookDTO> searchedBook = data.results()
                .stream()
                .filter(l -> l.title().toUpperCase().contains(titleBook.toUpperCase()))
                .findFirst();

        if (searchedBook.isPresent()) {
            BookDTO bookDTO = searchedBook.get();
            Book book = new Book(bookDTO);

            Optional<Book> existingBook = bookRepository.findByTitleContainingIgnoreCase(book.getTitle()).stream().findFirst();
            if (existingBook.isPresent()) {
                System.out.println("|-------------------------------------------------------------------------------------|");
                System.out.println("|------------------------------    ! Book was found    -------------------------------|");
                System.out.println(book);
                System.out.println("|-----------------------    # This book was not registered    ------------------------|");
                System.out.println("|-------------------------    # This book already exists    --------------------------|");
                return;
            }

            List<Author> authors = new ArrayList<>();
            for (AuthorDTO authorDTO : bookDTO.authors()) {
                Author author = new Author(authorDTO);
                Optional<Author> existingAuthor = authorRepository.findByNameContainingIgnoreCase(author.getName()).stream().findFirst();
                if (existingAuthor.isPresent()) {
                    author = existingAuthor.get();
                } else {
                    author = authorRepository.save(author);
                }

                authors.add(author);
            }

            book.setAuthors(authors);
            bookRepository.save(book);

            System.out.println("|---------------------------------    BOOK FOUND    ----------------------------------|");
            System.out.println(book);
            System.out.println("|-------------------------    ! This book was registered    --------------------------|");

        } else {
            System.out.println("|----------------------------    # Book was not found    -----------------------------|");
        }

    }

    public void getRegisteredBooks() {
        List<Book> books = bookRepository.findAll();

        if (books.isEmpty()) {
            System.out.println("|-------------------------    # No registered books found    -------------------------|");
            return;
        }
        DoubleSummaryStatistics stats = books.stream()
                .collect(Collectors.summarizingDouble(book -> 1.0));

        System.out.println("|------------------------------    Registered Books    -------------------------------|");
        System.out.println("Number of books registered : " + stats.getCount());
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void getRegisteredAuthors() {
        List<Author> authors = authorRepository.findAll();

        if (authors.isEmpty()) {
            System.out.println("|------------------------    # No registered authors found    ------------------------|");
            return;
        }

        DoubleSummaryStatistics stats = authors.stream()
                .collect(Collectors.summarizingDouble(book -> 1.0));

        System.out.println("|-----------------------------    Registered Authors    ------------------------------|");
        System.out.println(" Number of authors found : " + stats.getCount());
        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            String authorString = String.format("[%d] %s", i + 1, author);
            System.out.println(authorString);
        }
    }

    public void getAuthorLiveInDeterminedYear(Integer determinedYear) {
        String url = URL_BASE + "?author_year_end=" + determinedYear;
        String json = apiService.getResponse(url);
        GutendexData data = dataConverter.getData(json, GutendexData.class);

        List<Author> authorsFound = new ArrayList<>();
        List<BookDTO> booksFound = data.results();

        if (booksFound.isEmpty()) {
            System.out.println("|-----------------------------    # Authors not found   ------------------------------|");
            return;
        }

        for (BookDTO bookDto : booksFound) {
            for (AuthorDTO authorDTO : bookDto.authors()) {
                Author author = new Author(authorDTO);
                if (author.getDeathYear() <= determinedYear) {
                    if (!isAuthorInList(authorsFound, author)) {
                        authorsFound.add(author);
                    }
                }
            }
        }

        if (authorsFound.isEmpty()) {
            System.out.println("|-----------------------------    # Authors not found   ------------------------------|");
            return;
        }

        DoubleSummaryStatistics stats = authorsFound.stream()
                .collect(Collectors.summarizingDouble(book -> 1.0));
        System.out.println("|--------------------------------    Living authors   --------------------------------|");
        System.out.println("Number of authors found : " + stats.getCount());
        int authorIndex = 1;
        for (Author author : authorsFound) {
            String authorString = String.format("[%d] %s", authorIndex++, author);
            System.out.println(authorString);
        }
    }

    // Use a derived queries
    /*
    public void getAuthorLivingUntilYear(int determinedYear) {
        List<Author> authorsFound = authorRepository.findAuthorsLivingUntilYear(determinedYear);

        if (authorsFound.isEmpty()) {
            System.out.println("|-----------------------------    # Authors not found   ------------------------------|");
            return;
        }

        System.out.println("|--------------------------------    Living authors   --------------------------------|");
        int authorIndex = 1;
        for (Author author : authorsFound) {
            String authorString = String.format("[%d] %s", authorIndex++, author);
            System.out.println(authorString);
        }
    }
    */

    public boolean isAuthorInList(List<Author> authorsFound, Author authorToCheck) {
        for (Author author : authorsFound) {
            if (author.getName().equals(authorToCheck.getName())) {
                return true;
            }
        }
        return false;
    }

    public void getBooksByLanguages(String codeLanguage) {

        Languages language = Languages.fromString(codeLanguage);

        if (language.equals(Languages.UNKNOWN)) {
            System.out.println("|-----------------------------    # Unknown language    ------------------------------|");
            return;
        }

        List<Book> books = bookRepository.findAll()
                .stream()
                .filter(book -> book.getLanguages().contains(language))
                .toList();

        DoubleSummaryStatistics stats = books.stream()
                .collect(Collectors.summarizingDouble(book -> 1.0));

        if (books.isEmpty()) {
            System.out.println("|------------------------------    # No books found    -------------------------------|");
        } else {
            System.out.println("|---------------------------------    BOOKS FOUND    ---------------------------------|");
            System.out.println("Number of books in this language : " + stats.getCount());
            books.forEach(book -> System.out.println(book.toString()));
        }

    }

    public void getBooksByTitle(String bookName) {

        List<Book> booksFound = bookRepository.findByTitleContainingIgnoreCase(bookName);

        if (booksFound.isEmpty()) {
            System.out.println("|-------------------------    # No registered books found    -------------------------|");
            return;
        }

        DoubleSummaryStatistics stats = booksFound.stream()
                .collect(Collectors.summarizingDouble(book -> 1.0));
        System.out.println("|---------------------------------    BOOKS FOUND    ---------------------------------|");
        System.out.println("Number of books found : " + stats.getCount());
        booksFound.forEach(book -> System.out.println(book.toString()));
    }

    public void getAuthorsByName(String authorName) {
        List<Author> authorsFound = authorRepository.findByNameContainingIgnoreCase(authorName);

        if (authorsFound.isEmpty()) {
            System.out.println("|------------------------    # No registered authors found    ------------------------|");
            return;
        }

        DoubleSummaryStatistics stats = authorsFound.stream()
                .collect(Collectors.summarizingDouble(book -> 1.0));

        System.out.println("|--------------------------------    AUTHORS FOUND    --------------------------------|");
        System.out.println("Number of authors found : " + stats.getCount());
        for(int i = 0; i < authorsFound.size(); i++){
            Author author = authorsFound.get(i);
            String authorString = String.format("[%d] %s", i+1, author);
            System.out.println(authorString);
        }
    }

    public void getTopDownloadedBooks(int nTop) {
        List<Book> books = bookRepository.findAll();

        List<Book> topDownloadedBooks = books.stream()
                .sorted(Comparator.comparingInt(Book::getDownloadCount).reversed())
                .limit(nTop)
                .toList();

        if (topDownloadedBooks.isEmpty()) {
            System.out.println("|-----------------------    # No top downloaded books found    -----------------------|");
            return;
        }

        System.out.println("|---------------------------    # TOP DOWNLOADED BOOKS    ----------------------------|");
        int bookIndex = 1;
        for (Book book : topDownloadedBooks) {
            String bookString = String.format("TOP %d\n %s", bookIndex++, book);
            System.out.println(bookString);
        }
    }
}
