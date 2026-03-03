package com.devsenior.diego.bibliokeep.model.dto.request;

import com.devsenior.diego.bibliokeep.model.BookStatus;
import jakarta.validation.constraints.*;

import java.util.List;

public record BookRequestDTO(
        @NotBlank(message = "El ISBN es obligatorio") @Size(min = 10, max = 13, message = "El ISBN debe tener entre 10 y 13 dígitos") @Pattern(regexp = "^[0-9]{10,13}$", message = "El ISBN debe contener solo números")
        String isbn,

        @NotBlank(message = "El título es obligatorio") @Size(max = 500, message = "El título no puede exceder 500 caracteres")
        String title,

        List<@NotBlank(message = "El autor no puede estar vacío") @Size(max = 200, message = "El nombre del autor no puede exceder 200 caracteres")
        String> authors,

        @Size(max = 5000, message = "La descripción no puede exceder 5000 caracteres")
        String description,

        @Size(max = 1000, message = "La URL de la imagen no puede exceder 1000 caracteres")
        String thumbnail,

        @NotNull(message = "El estado es obligatorio")
        BookStatus status,

        @Min(value = 1, message = "La calificación mínima es 1") @Max(value = 5, message = "La calificación máxima es 5")
        Integer rating) {
}
