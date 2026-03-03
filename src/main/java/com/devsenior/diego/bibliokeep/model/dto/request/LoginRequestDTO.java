package com.devsenior.diego.bibliokeep.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
    @NotEmpty(message = "El campo de email de ususario es obligatorio")
    @Email(message = "El correo electronico tiene un formato invalido")
    String email,

    @NotEmpty(message = "El campo de clave es obligatorio")
    @Size(min = 6, message = "Debe tener minimo 6 caracteres")
    String password
    
  ) {
}