package com.hackathon.backend.service.user;

import com.hackathon.backend.dto.security.RegistrationDto;
import com.hackathon.backend.dto.user.UserDto;
import com.hackathon.backend.model.User;
import com.hackathon.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(String login) throws Exception {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new Exception(String.format("User with login %s doesn't exist", login)));
    }

    public boolean userExists(String login) {
        return userRepository.existsUserByLogin(login);
    }

    public UserDto getUserDto(String login) throws Exception {
        return userRepository.findByLogin(login)
                .map(this::mapToUserDto)
                .orElseThrow(() -> new Exception(String.format("User with login %s doesn't exist", login)));
    }

    public UserDto registerUser(RegistrationDto registrationDto) throws Exception {
        if (userExists(registrationDto.getLogin())) {
            throw new Exception(String.format(
                    "User with login %s already exists", registrationDto.getLogin()));
        }
        User user = mapToUser(registrationDto);
        return mapToUserDto(saveUser(user));
    }

    private User mapToUser(RegistrationDto registrationDto) {
        return User.builder()
                .login(registrationDto.getLogin())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .build();
    }

    private UserDto mapToUserDto(User user) {
        return new UserDto(user.getLogin());
    }
}
