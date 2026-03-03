package com.devsenior.diego.bibliokeep.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.devsenior.diego.bibliokeep.mapper.BookMapper;
import com.devsenior.diego.bibliokeep.model.BookStatus;
import com.devsenior.diego.bibliokeep.model.dto.request.BookRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.BookResponseDTO;
import com.devsenior.diego.bibliokeep.model.entity.Book;
import com.devsenior.diego.bibliokeep.repository.BookRepository;

public class BookserviceTest {

    @Test
    public void testCreateBook() {

        // Arrange
        var bookRepository = mock(BookRepository.class);
        var bookMapper = mock(BookMapper.class);
        var service = new BookServiceImpl(bookRepository, bookMapper);

        var bookRequestDTO = new BookRequestDTO(
                "9781234567890",
                "Prueba de Libro",
                null,
                null,
                null,
                BookStatus.DESEADO,
                1);

        var bookEntity = Book.builder()
                .id(1L)
                .ownerId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .isbn("9781234567890")
                .title("Cualquier titulo")
                .authors(null)
                .description(null)
                .thumbnail(null)
                .status(BookStatus.DESEADO)
                .rating(1)
                .isLent(false)
                .build();

        var bookResponseDTO = new BookResponseDTO(
                1L,
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "9781234567890",
                "Cualquier titulo",
                null,
                null,
                null,
                BookStatus.DESEADO,
                1,
                false);

        when(bookMapper.toEntity(bookRequestDTO, UUID.fromString("550e8400-e29b-41d4-a716-446655440000")))
                .thenReturn(bookEntity);

        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);

        when(bookMapper.toResponseDTO(bookEntity)).thenReturn(bookResponseDTO);

        // Act

        var result = service.create(bookRequestDTO, UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));

        // Assert

        assertNotNull(result);
        assertEquals("Cualquier titulo", result.title());
    }

    @Test
    public void testGetBookById() {

        // Arrange
        var id = 1L;

        var bookRepository = mock(BookRepository.class);
        var bookMapper = mock(BookMapper.class);
        var service = new BookServiceImpl(bookRepository, bookMapper);

        var bookEntity = Book.builder()
                .id(1L)
                .ownerId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .isbn("9781234567890")
                .title("Cualquier titulo")
                .authors(null)
                .description(null)
                .thumbnail(null)
                .status(BookStatus.DESEADO)
                .rating(1)
                .isLent(false)
                .build();

        var bookResponseDTO = new BookResponseDTO(
                1L,
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "9781234567890",
                "Cualquier titulo",
                null,
                null,
                null,
                BookStatus.DESEADO,
                1,
                false);

        when(bookRepository.findById(id)).thenReturn(Optional.of(bookEntity));

        when(bookMapper.toResponseDTO(bookEntity)).thenReturn(bookResponseDTO);


        // Act

        var result = service.findById(id);

        // Assert

        assertNotNull(result);
        assertEquals(1L, result.id());

    }
}
