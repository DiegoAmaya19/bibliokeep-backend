package com.devsenior.diego.bibliokeep.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;

import com.devsenior.diego.bibliokeep.model.BookStatus;
import com.devsenior.diego.bibliokeep.model.dto.request.BookRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.BookResponseDTO;
import com.devsenior.diego.bibliokeep.service.JwtService;

@SpringBootTest
public class BookControllerIT {

    @Autowired
    private JwtService jwtService;

    private RestTestClient restTestClient;

    @BeforeEach
    public void init(WebApplicationContext context) {
        restTestClient = RestTestClient.bindToApplicationContext(context)
                .build();
    }

    @Test
    public void createBookWithoutAuth() throws Exception {

        var request = new BookRequestDTO(
                "1234567890",
                "Prueba de Libro",
                null,
                null,
                null,
                BookStatus.DESEADO,
                null);

        restTestClient.post()
                .uri("/api/books")
                .body(request)
                .exchange()
                .expectStatus().isForbidden();

    }

    @Test
    public void createBookAsAdmin() throws Exception {

        var claims = Map.<String, Object>of(
                "roles", List.of("ROLE_ADMIN"),
                "user-id", "e7b1c9d2-3f4a-4b6c-9d8e-0a1b2c3d4e5f");

        var token = jwtService.generateToken(claims, "cdiaz@test.com", 60000);

        var request = new BookRequestDTO(
                "1234567890",
                "Prueba de Libro",
                null,
                null,
                null,
                BookStatus.DESEADO,
                null);

        var response = restTestClient.post()
                .uri("/api/books")
                .header("Authorization", String.format("Bearer %s", token))
                .body(request)
                .exchange()
                .expectStatus().isForbidden()
                .returnResult(BookResponseDTO.class)
                .getResponseBody();

        assertNotNull(response);
        assertNotNull(response.id());
    }

}