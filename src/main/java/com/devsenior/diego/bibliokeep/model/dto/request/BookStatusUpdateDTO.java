package com.devsenior.diego.bibliokeep.model.dto.request;

import com.devsenior.diego.bibliokeep.model.BookStatus;
import jakarta.validation.constraints.NotNull;

public record BookStatusUpdateDTO(
    @NotNull(message = "El estado es obligatorio")
    BookStatus status
) {
}
