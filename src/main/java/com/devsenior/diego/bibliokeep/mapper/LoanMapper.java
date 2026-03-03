package com.devsenior.diego.bibliokeep.mapper;

import com.devsenior.diego.bibliokeep.model.dto.request.LoanRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.LoanResponseDTO;
import com.devsenior.diego.bibliokeep.model.entity.Loan;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LoanMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "returned", constant = "false")
    Loan toEntity(LoanRequestDTO dto);

    LoanResponseDTO toResponseDTO(Loan entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "returned", ignore = true)
    void updateEntityFromDTO(LoanRequestDTO dto, @MappingTarget Loan entity);
}
