# 📖🔍 Book Fider 
In this exciting programming challenge, I developed an interactive book searcher. During this project, I learned how to make requests to a book API, manipulate JSON data, store it in a database, and filter and display books and authors of interest. This project was developed as part of my training in Java and Spring Boot G6 - ONE from [@AluraLatam](https://www.linkedin.com/company/alura-latam/). I hope you enjoy exploring the book catalog as much as I enjoyed creating it!

<p align="center">
  <img src="https://github.com/D-Chevez/Book-Finder/blob/Develop/src/main/resources/images/Alura%2BOracle.jpg" alt="Banner de Alura Latam + Oracle"/>
</p>



## 🎯 Objetive 
Develop a Book Finder that offers textual interaction (via console) with users, providing different interaction options. Books will be searched through a specific API, and these searches will be stored in a database. (In this case, a connection with PostgreSQL is used).

## 🛠 Tools and Technologies Used
- Java 17
- Lombok
- Jackson
- PostgreSQL
- Spring Framework
- Maven
- Hibernate
- JPA



## ⚙ Features 
The catalog offers the following user interaction options:

1. Search for books by title: The API is queried, and this record is saved in the database.
2. List registered books: All books registered in the database are displayed.
3. List registered authors: All authors registered in the database are displayed.
4. Search for authors alive up to a certain year: The API and database are queried for authors alive up to a specific date. (To use database option, uncomment the "getAuthorLivingUntilYear(int determinedYear)" function).
```java
public class GutendexService {
    public void getAuthorLivingUntilYear(int determinedYear) {
        //Function code
    }
}
```
5. List books by language: The database is queried for books in a specific language.
6. Search for authors by name: The database is queried for authors with a specific name.
7. List books by title: The database is queried for books with a specific title.
8. List most downloaded books: The database is queried for the most downloaded registered books, showing a top 10.



## 🚀 How to Run the Project
- Clone the repository.
- Configure the database in the application.properties file.
- Run the application from the main class BookFinderApplication.
- Interact with the book catalog through the options presented in the console.

## 👨‍💼 Authors 

- [@DiegoChevez](https://github.com/D-Chevez)
