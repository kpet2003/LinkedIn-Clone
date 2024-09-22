package com.tediproject.tedi.config;

import java.util.Arrays;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tediproject.tedi.security.CustomUserDetailsService;
import com.tediproject.tedi.security.JwtAuthenticationFilter;
import com.tediproject.tedi.security.JwtEntryPoint;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    private final JwtEntryPoint jwtEntryPoint;


    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtEntryPoint jwtEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.jwtEntryPoint = jwtEntryPoint;
    }
    
    // sets up security for the app
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http 
            // enable cors, according to the configuration in corsConfigurationSource
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // disable csrf protection
            .csrf(csrf -> csrf.disable()) 
            // allow all requests made to /SignUp/signup and welcome page; for all other pages the user has to be authenticated
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/static/css/**", "/static/js/**", "/**", "/ws/**").permitAll()
                .requestMatchers("/").permitAll() 
                .requestMatchers("/SignUp/signup").permitAll() 
                .requestMatchers("/AdminPage").hasRole("admin") // only admin can reach adminPage
                .requestMatchers("/HomePage").hasRole("user")   // only users can reach homePage

                .anyRequest().authenticated()) 
                // configuration related to jwt tokens
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(jwtEntryPoint))
                // creates stateless sessions
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
                // jwt authentication
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) 
            .build(); 
    }



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    // encrypts the passwords
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        // the REST API methods allowed
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        // any domain can make requests to the server
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        // send header to the client
        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true);
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}



}
