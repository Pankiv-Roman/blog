package com.pankiv.blog.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
                .requestMatchers(GET,"/api/v1/posts/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(POST,"/api/v1/posts").hasAuthority("ADMIN")
                .requestMatchers(POST, "/api/v1/posts/**}").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(DELETE,"/api/v1/posts/{id}").hasAuthority("ADMIN")
                .requestMatchers(PUT,"/api/v1/posts/{id}").hasAuthority("ADMIN")
                .requestMatchers(PUT,"/api/v1/posts/{id}/star").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(DELETE,"/api/v1/posts/{id}/star").hasAnyAuthority("ADMIN", "USER")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}