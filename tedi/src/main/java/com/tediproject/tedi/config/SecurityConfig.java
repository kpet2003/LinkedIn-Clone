package com.tediproject.tedi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tediproject.tedi.security.CustomUserDetailsService;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http 
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection for APIs
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/static/css/**", "/static/js/**", "/**").permitAll() // Allow static resources
                .requestMatchers("/").permitAll() // Allow access to the root endpoint
                .requestMatchers("/SignUp/signup").permitAll() // Allow sign-up endpoint
                .requestMatchers("/Login").permitAll() // Allow login endpoint
                .anyRequest().authenticated()) // Secure other endpoints
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
            .build();
    }



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    // @Bean
    // public CorsConfigurationSource corsConfigurationSource() {
    //     CorsConfiguration configuration = new CorsConfiguration();
    //     configuration.setAllowedOrigins(List.of("http://localhost:3000")); // React app URL
    //     configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    //     configuration.setAllowedHeaders(List.of("*"));
    //     configuration.setAllowCredentials(true); // Allow credentials
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     source.registerCorsConfiguration("/**", configuration);
    //     return source;
    // }


}
