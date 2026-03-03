package com.devsenior.diego.bibliokeep.service.impl;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.devsenior.diego.bibliokeep.model.dto.request.LoginRequestDTO;
import com.devsenior.diego.bibliokeep.model.dto.response.LoginResponseDTO;
import com.devsenior.diego.bibliokeep.service.AuthService;
import com.devsenior.diego.bibliokeep.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

   private final AuthenticationManager authenticationManager;
   private final UserService userService;
   private final JwtServiceImpl jwtService;

   @Override
   public LoginResponseDTO login(LoginRequestDTO body) {

      var user = userService.findByEmail(body.email())
            .orElseThrow(() -> new RuntimeException("User not found"));

      try {
         var auth = new UsernamePasswordAuthenticationToken(body.email(), body.password());
         authenticationManager.authenticate(auth);
      } catch (BadCredentialsException e) {
         throw new RuntimeException("Credenciles incorrectas " + e.getMessage());
      }

      var claims = Map.<String, Object>of(
            "X-User-Id", user.getId(),
            "roles", user.getRoles().stream()
                  .map(r -> r.getName())
                  .toList());

      var token = jwtService.generateToken(claims, body.email());

      return new LoginResponseDTO(token, "JWT");
   }

}
