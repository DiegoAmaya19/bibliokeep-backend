package com.devsenior.diego.bibliokeep.model.dto.response;

import com.devsenior.diego.bibliokeep.model.BookStatus;

import java.util.List;
import java.util.UUID;

public record BookResponseDTO(
    Long id,
    UUID ownerId,
    String isbn,
    String title,
    List<String> authors,
    String description,
    String thumbnail,
    BookStatus status,
    Integer rating,
    Boolean isLent
) {
}
