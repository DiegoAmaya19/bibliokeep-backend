package com.devsenior.diego.bibliokeep.service;

import com.devsenior.diego.bibliokeep.model.dto.request.LoanRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.LoanResponseDTO;

import java.util.List;
import java.util.UUID;

public interface LoanService {

    LoanResponseDTO create(LoanRequestDTO request, UUID ownerId);

    List<LoanResponseDTO> findAllByOwnerId(UUID ownerId);

    LoanResponseDTO findById(Long id, UUID ownerId);

    LoanResponseDTO update(Long id, LoanRequestDTO request, UUID ownerId);

    void delete(Long id, UUID ownerId);

    LoanResponseDTO markAsReturned(Long id, UUID ownerId);
}
