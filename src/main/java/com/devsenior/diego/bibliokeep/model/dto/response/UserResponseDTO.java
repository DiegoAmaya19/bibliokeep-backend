package com.devsenior.diego.bibliokeep.model.dto.response;

import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(
    UUID id,
    String email,
    Set<String> preferences,
    Integer annualGoal
) {
}
