package com.devsenior.diego.bibliokeep.model.dto.request;

import jakarta.validation.constraints.*;

import java.util.Set;

public record UserRequestDTO(
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    String email,

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    String password,

    Set<
    @NotBlank(message = "El género no puede estar vacío") 
    @Size(max = 100, message = "El género no puede exceder 100 caracteres") 
    String>
    preferences,

    @Min(value = 1, message = "La meta anual debe ser al menos 1")
    @Max(value = 1000, message = "La meta anual no puede exceder 1000")
    Integer annualGoal,

    Set<Long> rolesId
) {
}
