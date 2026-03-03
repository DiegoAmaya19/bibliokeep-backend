package com.devsenior.diego.bibliokeep.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "loans", indexes = {
    @Index(name = "idx_loan_book", columnList = "book_id"),
    @Index(name = "idx_loan_due_date", columnList = "due_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id", nullable = false)
    @NotNull(message = "El libro es obligatorio")
    private Long bookId;

    @Column(name = "contact_name", nullable = false)
    @NotBlank(message = "El nombre del contacto es obligatorio")
    @Size(max = 200, message = "El nombre del contacto no puede exceder 200 caracteres")
    private String contactName;

    @Column(name = "loan_date", nullable = false)
    @NotNull(message = "La fecha de préstamo es obligatoria")
    private LocalDate loanDate;

    @Column(name = "due_date", nullable = false)
    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDate dueDate;

    @Column(nullable = false)
    @NotNull(message = "El estado de devolución es obligatorio")
    @Builder.Default
    private Boolean returned = false;
}
