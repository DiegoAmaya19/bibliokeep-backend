package com.devsenior.diego.bibliokeep.model.dto.response;

public record DashboardResponseDTO(
    Integer annualGoal,
    Long booksReadThisYear,
    Double progressPercentage,
    Long totalBooks,
    Long activeLoans,
    Long overdueLoans
) {
}
