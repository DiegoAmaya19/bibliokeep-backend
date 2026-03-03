package com.devsenior.diego.bibliokeep.mapper;

import com.devsenior.diego.bibliokeep.model.dto.request.UserRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.UserResponseDTO;
import com.devsenior.diego.bibliokeep.model.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) // Se manejará en el servicio con BCrypt
    @Mapping(target = "annualGoal", defaultValue = "12")
    User toEntity(UserRequestDTO dto);

    // "password" does not exist on UserResponseDTO, so we remove the invalid
    // mapping
    UserResponseDTO toResponseDTO(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDTO(UserRequestDTO dto, @MappingTarget User entity);
}
