package com.hackathon.backend.controller.security;

import com.hackathon.backend.dto.security.LoginRequestDto;
import com.hackathon.backend.dto.security.RegistrationDto;
import com.hackathon.backend.dto.user.UserDto;
import com.hackathon.backend.security.JwtProvider;
import com.hackathon.backend.security.JwtUserDetailsService;
import com.hackathon.backend.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/hackathon/api/v1")
public class SecurityController {
    private final JwtUserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public SecurityController(JwtUserDetailsService userDetailsService, JwtProvider jwtProvider,
                              PasswordEncoder passwordEncoder, UserService userService) {
        this.userDetailsService = userDetailsService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDto loginRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getLogin());

            if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword()))
                throw new BadCredentialsException("Password is incorrect");

            String jwtToken = jwtProvider.generateToken(userDetails);
            return ResponseEntity.ok(jwtToken);
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Login or password is incorrect.");
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegistrationDto registrationDto) {
        try {
            UserDto newUser = userService.registerUser(registrationDto);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}
