package com.develop.BookFinder;

import com.develop.BookFinder.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookFinderApplication implements CommandLineRunner {

	@Autowired
	private Application application;

	public static void main(String[] args) {
		SpringApplication.run(BookFinderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		application.run();
	}
}
