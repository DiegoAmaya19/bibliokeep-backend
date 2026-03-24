package com.devsenior.diego.bibliokeep.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

   @Value("${app.cors.allowed-origins}")
   private String allowedOrigins;

   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) {

      var corsConfigurationSouerce = corsConfigurationSource();

      http
            .csrf(c -> c.disable()) // desactiva las sesiones por seguridad
            .cors(cors -> cors.configurationSource(corsConfigurationSouerce)) // maneja de los origenes de peticiones
            .authorizeHttpRequests(auth -> auth
                  .requestMatchers("/auth/**").permitAll()
                  .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                  .anyRequest().authenticated())
                     .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
      return http.build();
   }

   // Esta configuracion es para el uso del cors de donde se consumira la Api
   // Se coloca en el SecurityFilterChain
   private CorsConfigurationSource corsConfigurationSource() {
      var corsConfiguration = new CorsConfiguration();
      
      // Nota: para que acceda todoo el mundo "*"
      corsConfiguration.setAllowedOrigins(List.of(allowedOrigins.split(","))); 
      corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTION"));
      corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Tenant-ID", "X-User-id"));
      corsConfiguration.setAllowCredentials(true);

      var source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", corsConfiguration);// quiere decir que para cualquier perticion a mi app
                                                                 
      // se
      // usa
      // esta configuracion
      return source;
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   AuthenticationManager authenticationManager(AuthenticationConfiguration corsConfiguration) {
      return corsConfiguration.getAuthenticationManager();
   }
}
