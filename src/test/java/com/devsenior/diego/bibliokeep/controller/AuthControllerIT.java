package com.devsenior.diego.bibliokeep.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.devsenior.diego.bibliokeep.model.dto.request.LoginRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.LoginResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest()
@AutoConfigureMockMvc
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Inicio de secion con usuario y contrasena")
    public void loginSuccess() throws JsonProcessingException, Exception {
        // Arrange
        var request = new LoginRequestDTO(
            "diego@example.com", 
            "diego0925696");

        // Act
        var result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

                var response = mapper.readValue(result.getResponse().getContentAsString(),
                        LoginResponseDTO.class);
        // Assert
        assertNotNull(response);
        assertNotNull(response.accessToken());
    }
}
