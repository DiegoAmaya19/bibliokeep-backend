package com.devsenior.diego.bibliokeep.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsenior.diego.bibliokeep.model.dto.request.LoginRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.LoginResponseDTO;
import com.devsenior.diego.bibliokeep.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginIn(@RequestBody @Valid LoginRequestDTO body) {

        return ResponseEntity.ok(authService.login(body));
    }

}
