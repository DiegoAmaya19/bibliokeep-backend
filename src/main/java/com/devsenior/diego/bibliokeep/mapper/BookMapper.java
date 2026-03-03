package com.devsenior.diego.bibliokeep.mapper;

import com.devsenior.diego.bibliokeep.model.dto.request.BookRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.BookResponseDTO;
import com.devsenior.diego.bibliokeep.model.entity.Book;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "isLent", constant = "false")
    Book toEntity(BookRequestDTO dto, UUID ownerId);

    BookResponseDTO toResponseDTO(Book entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerId", ignore = true)
    @Mapping(target = "isLent", ignore = true)
    void updateEntityFromDTO(BookRequestDTO dto, @MappingTarget Book entity);
}
