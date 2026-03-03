package com.devsenior.diego.bibliokeep.service;

import com.devsenior.diego.bibliokeep.model.BookStatus;
import com.devsenior.diego.bibliokeep.model.dto.request.BookRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.BookResponseDTO;

import java.util.List;
import java.util.UUID;

public interface BookService {

    BookResponseDTO create(BookRequestDTO request, UUID ownerId);

    List<BookResponseDTO> todosLosLibros();

    List<BookResponseDTO> findAllByOwnerId(UUID ownerId);

    BookResponseDTO findById(Long id);

    BookResponseDTO findById(Long id, UUID ownerId);

    BookResponseDTO update(Long id, BookRequestDTO request, UUID ownerId);

    BookResponseDTO updateStatus(Long id, BookStatus status, UUID ownerId);

    void delete(Long id, UUID ownerId);
}
