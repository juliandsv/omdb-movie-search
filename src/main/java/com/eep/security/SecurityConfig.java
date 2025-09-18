package com.eep.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {
	 private final UserDetailsService userDetailsService;

	    public SecurityConfig(UserDetailsService userDetailsService) {
	        this.userDetailsService = userDetailsService;
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	        // Configurar el AuthenticationManager directamente
	        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
	        return authenticationManager;
	    }

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        return http
	                .authorizeHttpRequests(authz -> authz
	                        .requestMatchers(HttpMethod.GET, "/login").permitAll()
	                        .requestMatchers(HttpMethod.GET, "/registro").permitAll()
	                        .requestMatchers(HttpMethod.POST, "/registro").permitAll()
	                        .requestMatchers("/buscar").authenticated() // Solo autenticados
	                        .anyRequest().authenticated()
	                    )
	                    .formLogin(form -> form
	                        .loginPage("/login") 
	                        .defaultSuccessUrl("/buscar", true) 
	                    )
	                    
	                    .sessionManagement(session -> session
	                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) 
	                    .userDetailsService(userDetailsService) 
	                    .build();
	            }
	    
    }
	
	
	 
	


