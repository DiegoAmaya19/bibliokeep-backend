package com.devsenior.diego.bibliokeep.service.impl;

import com.devsenior.diego.bibliokeep.mapper.BookMapper;
import com.devsenior.diego.bibliokeep.model.BookStatus;
import com.devsenior.diego.bibliokeep.model.dto.request.BookRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.BookResponseDTO;
import com.devsenior.diego.bibliokeep.repository.BookRepository;
import com.devsenior.diego.bibliokeep.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public BookResponseDTO create(BookRequestDTO request, UUID ownerId) {
        var book = bookMapper.toEntity(request, ownerId);
        book = bookRepository.save(book);
        return bookMapper.toResponseDTO(book);
    }

    @Override
    public List<BookResponseDTO> findAllByOwnerId(UUID ownerId) {
        var books = bookRepository.findByOwnerId(ownerId);
        return books.stream()
                .map(bookMapper::toResponseDTO)
                .toList();
    }

    @Override
    public BookResponseDTO findById(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado o no pertenece al usuario"));
        return bookMapper.toResponseDTO(book);
    }

    @Override
    public BookResponseDTO findById(Long id, UUID ownerId) {
        var book = bookRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado o no pertenece al usuario"));
        return bookMapper.toResponseDTO(book);
    }

    @Override
    @Transactional
    public BookResponseDTO update(Long id, BookRequestDTO request, UUID ownerId) {
        var book = bookRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado o no pertenece al usuario"));

        bookMapper.updateEntityFromDTO(request, book);
        book = bookRepository.save(book);

        return bookMapper.toResponseDTO(book);
    }

    @Override
    @Transactional
    public BookResponseDTO updateStatus(Long id, BookStatus status, UUID ownerId) {
        var book = bookRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado o no pertenece al usuario"));

        book.setStatus(status);
        book = bookRepository.save(book);

        return bookMapper.toResponseDTO(book);
    }

    @Override
    @Transactional
    public void delete(Long id, UUID ownerId) {
        var book = bookRepository.findByIdAndOwnerId(id, ownerId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado o no pertenece al usuario"));

        if (book.getIsLent()) {
            throw new RuntimeException("No se puede eliminar un libro que está prestado");
        }

        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponseDTO> todosLosLibros() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponseDTO)
                .toList();
    }
}
