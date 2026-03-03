package com.devsenior.diego.bibliokeep.service.impl;

import com.devsenior.diego.bibliokeep.mapper.UserMapper;
import com.devsenior.diego.bibliokeep.model.dto.request.UserRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.UserResponseDTO;
import com.devsenior.diego.bibliokeep.model.entity.Role;
import com.devsenior.diego.bibliokeep.model.entity.User;
import com.devsenior.diego.bibliokeep.repository.UserRepository;
import com.devsenior.diego.bibliokeep.service.RoleService;
import com.devsenior.diego.bibliokeep.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    @Transactional
    public UserResponseDTO create(UserRequestDTO request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user = userRepository.save(user);

        List<Role> listRoles = roleService.findAllByIdList(request.rolesId()).stream().toList();

        user.setRoles(listRoles);

        return userMapper.toResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> findAll() {
        var users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO findById(UUID id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO update(UUID id, UserRequestDTO request) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        var existingUser = userRepository.findByEmail(request.email());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
            throw new RuntimeException("El email ya está registrado por otro usuario");
        }

        userMapper.updateEntityFromDTO(request, user);

        // if (request.password() != null && !request.password().isEmpty()) {
        // user.setPassword(passwordEncoder.encode(request.password()));
        // }

        user = userRepository.save(user);

        return userMapper.toResponseDTO(user);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean exisitsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
