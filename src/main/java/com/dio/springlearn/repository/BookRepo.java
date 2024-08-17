package com.dio.springlearn.repository;

import com.dio.springlearn.models.BookModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<BookModels, Long> {
    @Query(value = "SELECT * FROM books", nativeQuery = true)
    Iterable<BookModels> findAllBooks();

    @Query(value = "SELECT * FROM books WHERE book_id = :id", nativeQuery = true)
    BookModels findBookById(int id);

//    @Query(value = "INSERT INTO books (title, author, description, genre, quantity, created_at) VALUES (:title, :author, :description, :genre, :quantity, :created_at)", nativeQuery = true)
//    int create(@Param("title") String title, @Param("author") String author, @Param("description") String description, @Param("genre") String genre, @Param("quantity") int quantity, @Param("created_at") String created_at);


}
