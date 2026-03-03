package com.devsenior.diego.bibliokeep.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDTO(
    @JsonProperty("access_token")
    String accessToken,

    String type
) {
}