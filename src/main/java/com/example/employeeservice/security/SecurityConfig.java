package com.example.employeeservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails hr = User.withUsername("hr")
                .password("{noop}hr123")
                .roles("HR")
                .build();

        UserDetails it = User.withUsername("it")
                .password("{noop}it123")
                .roles("IT")
                .build();

        return new InMemoryUserDetailsManager(hr, it);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ✅ Disabling CSRF for stateless REST API
                .csrf(AbstractHttpConfigurer::disable)

                // ✅ Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // Only HR can create employees
                        .requestMatchers(HttpMethod.POST, "/employees/**").hasRole("HR")

                        // Only HR can update employees
                        .requestMatchers(HttpMethod.PUT, "/employees/**").hasRole("HR")

                        // Only HR can delete employees
                        .requestMatchers(HttpMethod.DELETE, "/employees/**").hasRole("HR")

                        // Any authenticated user (HR, IT, etc.) can view
                        .requestMatchers(HttpMethod.GET, "/employees/**").authenticated()

                        // Fallback for any other endpoints
                        .anyRequest().authenticated()
                )

                // ✅ Use basic HTTP auth
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
