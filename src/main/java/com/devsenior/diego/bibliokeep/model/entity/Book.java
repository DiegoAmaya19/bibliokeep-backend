package com.devsenior.diego.bibliokeep.model.entity;

import com.devsenior.diego.bibliokeep.model.BookStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "books", indexes = {
    @Index(name = "idx_book_isbn", columnList = "isbn", unique = true),
    @Index(name = "idx_book_owner", columnList = "owner_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    @NotNull(message = "El propietario es obligatorio")
    private UUID ownerId;

    @Column(nullable = false, unique = true, length = 13)
    @NotBlank(message = "El ISBN es obligatorio")
    @Size(min = 10, max = 13, message = "El ISBN debe tener entre 10 y 13 dígitos")
    @Pattern(regexp = "^[0-9]{10,13}$", message = "El ISBN debe contener solo números")
    private String isbn;

    @Column(nullable = false)
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 500, message = "El título no puede exceder 500 caracteres")
    private String title;

    @ElementCollection
    @CollectionTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "author")
    @Builder.Default
    private List<String> authors = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    @Size(max = 5000, message = "La descripción no puede exceder 5000 caracteres")
    private String description;

    @Column(length = 1000)
    @Size(max = 1000, message = "La URL de la imagen no puede exceder 1000 caracteres")
    private String thumbnail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "El estado es obligatorio")
    private BookStatus status;

    @Column
    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    private Integer rating;

    @Column(name = "is_lent", nullable = false)
    @NotNull(message = "El estado de préstamo es obligatorio")
    @Builder.Default
    private Boolean isLent = false;
    
}