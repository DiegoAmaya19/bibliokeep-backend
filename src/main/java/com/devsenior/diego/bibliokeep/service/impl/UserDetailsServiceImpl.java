package com.devsenior.diego.bibliokeep.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsenior.diego.bibliokeep.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

   private final UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      var user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("No existe un usuario con este email " + username));

      var authorities = user.getRoles().stream()
            .map(rol -> new SimpleGrantedAuthority(rol.getName()))
            .toList();

      return User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .authorities(authorities)
            .build();
   }
}