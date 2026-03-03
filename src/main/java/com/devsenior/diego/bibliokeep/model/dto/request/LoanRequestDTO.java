package com.devsenior.diego.bibliokeep.model.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record LoanRequestDTO(
    @NotNull(message = "El libro es obligatorio")
    Long bookId,

    @NotBlank(message = "El nombre del contacto es obligatorio")
    @Size(max = 200, message = "El nombre del contacto no puede exceder 200 caracteres")
    String contactName,

    @NotNull(message = "La fecha de préstamo es obligatoria")
    LocalDate loanDate,

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    LocalDate dueDate
) {
}
