package com.dio.springlearn.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class BookModels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @NotBlank(message = "Title is required")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Author is required")
    @Column(name = "author")
    private String author;

    @NotBlank(message= "Description is required")
    @Column(name = "description")
    private String description;

    @NotBlank(message= "Genre is required")
    @Column(name = "genre")
    private String genre;

    @NotBlank(message = "Quantity is required")
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "created_at")
    private String createdAt;

    
    @Override
    public String toString() {
        return "BookModels{"
                + "bookId=" + bookId
                + ", title='" + title + '\''
                + ", author='" + author + '\''
                + ", description='" + description + '\''
                + ", genre='" + genre + '\''
                + ", quantity=" + quantity
                + ", createdAt='" + createdAt + '\''
                + '}';
    }
}
