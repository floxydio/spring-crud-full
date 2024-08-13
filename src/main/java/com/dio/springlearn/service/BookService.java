package com.dio.springlearn.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.springlearn.models.BookModels;
import com.dio.springlearn.repository.BookRepo;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepo bookRepo;

    public Iterable<BookModels> findBook() {
        logger.info("Requesting all books");
        return bookRepo.findAllBooks();
    }

    public BookModels findBookById(int id) {
        logger.info("findBookID");
        logger.info(String.valueOf(id));
        return bookRepo.findBookById(id);
    }

    public BookModels createBook(BookModels booksModels) {
        String createdAts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        logger.info("bookModel from service ->", booksModels.toString());
        booksModels.setCreatedAt(createdAts);
        return bookRepo.save(booksModels);
    }

}
