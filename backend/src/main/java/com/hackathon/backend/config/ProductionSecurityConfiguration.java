package com.hackathon.backend.config;

import com.hackathon.backend.security.JwtRequestTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Order(1)
@EnableWebSecurity
@Profile("prod")
public class ProductionSecurityConfiguration implements WebMvcConfigurer {
    private final JwtRequestTokenVerifier jwtRequestTokenVerifier;

    @Autowired
    public ProductionSecurityConfiguration(JwtRequestTokenVerifier jwtRequestTokenVerifier) {
        this.jwtRequestTokenVerifier = jwtRequestTokenVerifier;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for JWT based authentication
        return httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(permittedEndpoints()).permitAll()
                .requestMatchers(authorizedEndpoints()).authenticated()
                .and()
                .addFilterBefore(jwtRequestTokenVerifier, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*");
    }

    private String[] permittedEndpoints() {
        return new String[] {
                "/hackathon/api/v1/login",
                "/hackathon/api/v1/registration"
        };
    }

    private String[] authorizedEndpoints() {
        return new String[] {
                "/hackathon/api/v1/user",
        };
    }
}