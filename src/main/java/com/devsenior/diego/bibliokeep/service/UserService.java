package com.devsenior.diego.bibliokeep.service;

import com.devsenior.diego.bibliokeep.model.dto.request.UserRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.UserResponseDTO;
import com.devsenior.diego.bibliokeep.model.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserResponseDTO create(UserRequestDTO request);

    List<UserResponseDTO> findAll();

    UserResponseDTO findById(UUID id);

    UserResponseDTO update(UUID id, UserRequestDTO request);

    void delete(UUID id);

    boolean exisitsEmail(String email);

    Optional<User> findByEmail(String email);
}
