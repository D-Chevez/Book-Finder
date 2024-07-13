package com.develop.BookFinder.application;


import com.develop.BookFinder.model.Languages;
import com.develop.BookFinder.service.GutendexService;
import com.develop.BookFinder.util.AppUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Application {

    private final Scanner scanner;

    @Autowired
    private GutendexService gutendexService;


    @Autowired
    public Application(GutendexService gutendexService) {
        this.scanner = new Scanner(System.in);
        this.gutendexService = gutendexService;
    }

    public void run() {
        String choice;

        System.out.println("""
                ---------------------------------------------------------------------------------------
                ------------------------------------    WELCOME    ------------------------------------
                ---------------------------------------------------------------------------------------
                """);

        do {
            System.out.println("""
                    -------------------------------------    MENU    --------------------------------------
                    | [1] - Search book by title                                                          |
                    | [2] - List registered books                                                         |
                    | [3] - List registered authors                                                       |
                    | [4] - List living authors in a specific year                                        |
                    | [5] - List books by language                                                        |
                    | [6] - List books by title                                                           |
                    | [7] - List authors by name                                                          |
                    | [8] - Search the 5 most downloaded books                                            |
                    |                                                                                     |
                    | [x] - Exit                                                                          |
                    |-------------------------------------------------------------------------------------|
                    """);
            System.out.print("Select an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    searchBookByTitle();
                    break;
                case "2":
                    listRegisteredBooks();
                    break;
                case "3":
                    listRegisteredAuthors();
                    break;
                case "4":
                    listAuthorsLiveInDeterminedYear();
                    break;
                case "5":
                    listBooksByLanguage();
                    break;
                case "6":
                    listBooksByTitle();
                    break;
                case "7":
                    listAuthorsByName();
                    break;
                case "8":
                    searchTopDownloadedBooks();
                    break;
                case "x":
                    AppUtil.finalizer();
                    break;
                default:
                    System.out.println("|---------------------    # Invalid option, please try again    ----------------------|");
            }
        } while (!choice.equals("x"));

    }

    private void searchTopDownloadedBooks() {
        gutendexService.getTopDownloadedBooks(10);

        String choice = "";
        while(!choice.equals("b")){
            System.out.println("""
                |-------------------------------------------------------------------------------------|
                |    [b]Go Back    |    [x]Exit                                                       |
                |-------------------------------------------------------------------------------------|
                """);
            System.out.print("Select an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "b":
                    break;
                case "x":
                    AppUtil.finalizer();
                    break;
                default:
                    System.out.println("----------------------    **Invalid option, please try again    -----------------------");
                    break;
            }
        }
    }

    private void listAuthorsByName() {
        System.out.print("Write the author name: ");
        String authorName = scanner.nextLine();

        gutendexService.getAuthorsByName(authorName);

        String choice;
        do{
            System.out.println("""
                |-------------------------------------------------------------------------------------|
                |    [f]Find Another    |    [b]Go Back    |    [x]Exit                               |
                |-------------------------------------------------------------------------------------|
                """);
            System.out.print("Select an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "f":
                    listAuthorsByName();
                    break;
                case "b":
                    break;
                case "x":
                    AppUtil.finalizer();
                    break;
                default:
                    System.out.println("----------------------    **Invalid option, please try again    -----------------------");
                    break;
            }
        }while(!choice.equals("b"));
    }

    private void listBooksByTitle() {
        System.out.print("Write the book name ");
        String bookName = scanner.nextLine();

        gutendexService.getBooksByTitle(bookName);

        String choice;
        do{
            System.out.println("""
                |-------------------------------------------------------------------------------------|
                |    [f]Find Another    |    [b]Go Back    |    [x]Exit                               |
                |-------------------------------------------------------------------------------------|
                """);
            System.out.print("Select an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "f":
                    listBooksByTitle();
                    break;
                case "b":
                    break;
                case "x":
                    AppUtil.finalizer();
                    break;
                default:
                    System.out.println("----------------------    **Invalid option, please try again    -----------------------");
                    break;
            }
        }while(!choice.equals("b"));
    }

    private void listBooksByLanguage() {
        System.out.println("Available language");
        for (Languages language : Languages.values()) {
            if (!language.equals(Languages.UNKNOWN)) {
                System.out.println(language + " (" + language.getCode() + ")");
            }
        }
        System.out.println("Select the language code:");
        String shearedlanguage = scanner.nextLine().toLowerCase();

        gutendexService.getBooksByLanguages(shearedlanguage);

        String choice;
        do{
            System.out.println("""
                |-------------------------------------------------------------------------------------|
                |                 [f]Find Another    |    [b]Go Back    |    [x]Exit                  |
                |-------------------------------------------------------------------------------------|
                """);
            System.out.print("Select an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "f":
                    listBooksByLanguage();
                    break;
                case "b":
                    break;
                case "x":
                    AppUtil.finalizer();
                    break;
                default:
                    System.out.println("----------------------    # Invalid option, please try again    -----------------------");
                    break;
            }
        }while(!choice.equals("b"));

    }

    private void listAuthorsLiveInDeterminedYear() {
        System.out.print("Write the year: ");
        try {
            int yearLiving = Integer.parseInt(scanner.nextLine());
            gutendexService.getAuthorLiveInDeterminedYear(yearLiving);
        }catch (NumberFormatException e){
            System.out.println("|-----------------    # Invalid input. Please enter a valid year    ------------------|");
        }


        String choice;
        do{
            System.out.println("""
                |-------------------------------------------------------------------------------------|
                |                 [f]Find Another    |    [b]Go Back    |    [x]Exit                  |
                |-------------------------------------------------------------------------------------|
                """);
            System.out.print("Select an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "f":
                    listAuthorsLiveInDeterminedYear();
                    break;
                case "b":
                    break;
                case "x":
                    AppUtil.finalizer();
                    break;
                default:
                    System.out.println("----------------------    # Invalid option, please try again    -----------------------");
                    break;
            }
        }while(!choice.equals("b"));

    }

    private void listRegisteredAuthors() {
        gutendexService.getRegisteredAuthors();

        String choice = "";
        while(!choice.equals("b")){
            System.out.println("""
                |-------------------------------------------------------------------------------------|
                |    [b]Go Back    |    [x]Exit                                                       |
                |-------------------------------------------------------------------------------------|
                """);
            System.out.print("Select an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "b":
                    break;
                case "x":
                    AppUtil.finalizer();
                    break;
                default:
                    System.out.println("----------------------    **Invalid option, please try again    -----------------------");
                    break;
            }
        }
    }

    private void listRegisteredBooks() {
        gutendexService.getRegisteredBooks();

        String choice = "";
        while(!choice.equals("b")){
            System.out.println("""
                |-------------------------------------------------------------------------------------|
                |    [b]Go Back    |    [x]Exit                                                       |
                |-------------------------------------------------------------------------------------|
                """);
            System.out.print("Select an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "b":
                    break;
                case "x":
                    AppUtil.finalizer();
                    break;
                default:
                    System.out.println("----------------------    **Invalid option, please try again    -----------------------");
                    break;
            }
        }
    }

    private void searchBookByTitle() {
        System.out.print("Write the title book: ");
        String titleBook = scanner.nextLine();

        gutendexService.getBookByTitle(titleBook);

        String choice = "";
        while(!choice.equals("b")){
            System.out.println("""
                |-------------------------------------------------------------------------------------|
                |    [f]Find Another    |    [b]Go Back    |    [x]Exit                               |
                |-------------------------------------------------------------------------------------|
                """);
            System.out.print("Select an option: ");
            choice = scanner.nextLine();

            switch (choice) {
                case "f":
                    searchBookByTitle();
                    break;
                case "b":
                    break;
                case "x":
                    AppUtil.finalizer();
                    break;
                default:
                    System.out.println("----------------------    **Invalid option, please try again    -----------------------");
                    break;
            }
        }

    }



}


