package com.dio.springlearn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dio.springlearn.entities.ResponseCreatedOrFailed;
import com.dio.springlearn.entities.ResponseOK;
import com.dio.springlearn.models.BookModels;
import com.dio.springlearn.service.BookService;

@RestController
@RequestMapping("/v1")
public class BookController {
    // private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    
    public ResponseEntity<ResponseOK> findBook() {
        Iterable<BookModels> bookData = bookService.findBook();

        if(!bookData.iterator().hasNext()) {
            return ResponseEntity.badRequest().body(new ResponseOK(400,true,null, "Failed to create book"));
        } else {
            return ResponseEntity.ok().body(new ResponseOK(200, false, bookData, "Successfully GET Book Data"));
        }

    }

    @GetMapping("/books/{id}")
    public ResponseEntity<ResponseOK> findBookById(@PathVariable("id") int id) {
        BookModels bookbyId = bookService.findBookById(id);

        if(bookbyId == null) {
            return ResponseEntity.badRequest().body(new ResponseOK(400,true,null, "Failed to create book"));
        } else {
            return ResponseEntity.ok().body(new ResponseOK(200, false, bookbyId, "Successfully GET Book Data"));
        }

    }

    @PostMapping("/books-create")
    public ResponseEntity<ResponseCreatedOrFailed> createBook(
            @RequestBody BookModels bookModels
    ) {
        BookModels book = bookService.createBook(bookModels);
    
        if(book == null) {
            return ResponseEntity.badRequest().body(new ResponseCreatedOrFailed(400,true, "Failed to create book"));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseCreatedOrFailed(201,false, "Book created successfully"));
        }

    }

}
