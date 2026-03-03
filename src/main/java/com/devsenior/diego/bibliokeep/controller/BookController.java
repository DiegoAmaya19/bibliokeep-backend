package com.devsenior.diego.bibliokeep.controller;

import com.devsenior.diego.bibliokeep.model.dto.request.BookRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.request.BookStatusUpdateDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.BookResponseDTO;
import com.devsenior.diego.bibliokeep.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "http://localhost:4200") // Permite el acceso desde Angular
@RestController
@AllArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDTO create(@Valid @RequestBody BookRequestDTO request,
            @RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        return bookService.create(request, ownerId);
    }

    @GetMapping
    public List<BookResponseDTO> findAll(@RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        return bookService.findAllByOwnerId(ownerId);
    }

    @GetMapping("/{id}")
    public BookResponseDTO findById(@PathVariable Long id,
            @RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        return bookService.findById(id, ownerId);
    }

    @PutMapping("/{id}")
    public BookResponseDTO update(@PathVariable Long id,
            @Valid @RequestBody BookRequestDTO request,
            @RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        return bookService.update(id, request, ownerId);
    }

    @PatchMapping("/{id}/status")
    public BookResponseDTO updateStatus(@PathVariable Long id,
            @Valid @RequestBody BookStatusUpdateDTO request,
            @RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        return bookService.updateStatus(id, request.status(), ownerId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id,
            @RequestHeader("X-User-Id") String userId) {
        var ownerId = UUID.fromString(userId);
        bookService.delete(id, ownerId);
    }

    @GetMapping("/all")
    public List<BookResponseDTO> todosLosLibros() {
        return bookService.todosLosLibros();
    }
}