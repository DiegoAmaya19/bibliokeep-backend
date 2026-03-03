package com.devsenior.diego.bibliokeep.model.dto.response;

import java.time.LocalDate;

public record LoanResponseDTO(
    Long id,
    Long bookId,
    String contactName,
    LocalDate loanDate,
    LocalDate dueDate,
    Boolean returned
) {
}
