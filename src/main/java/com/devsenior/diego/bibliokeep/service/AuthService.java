package com.devsenior.diego.bibliokeep.service;

import com.devsenior.diego.bibliokeep.model.dto.request.LoginRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.LoginResponseDTO;


public interface AuthService {

  LoginResponseDTO login(LoginRequestDTO body);
}