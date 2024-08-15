package com.tediproject.tedi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tediproject.tedi.security.CustomUserDetailsService;
import com.tediproject.tedi.security.JwtAuthenticationFilter;
import com.tediproject.tedi.security.JwtEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;


    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtEntryPoint jwtEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtEntryPoint = jwtEntryPoint;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http 
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/static/css/**", "/static/js/**", "/**").permitAll()
                .requestMatchers("/").permitAll() 
                .requestMatchers("/SignUp/signup").permitAll() 
                .requestMatchers("/AdminPage").hasRole("admin")
                .requestMatchers("/NewEmail").permitAll()
                .requestMatchers("/VisitProfile/**").permitAll()
                .requestMatchers("/Network/newRequest").authenticated()
                .requestMatchers("/Profile/edubool").authenticated()
                .anyRequest().authenticated()) 
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(jwtEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) 
            .build(); 
    }



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }



}
