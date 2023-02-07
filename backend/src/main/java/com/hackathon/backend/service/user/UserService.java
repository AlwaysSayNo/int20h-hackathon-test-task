package com.hackathon.backend.service.user;

import com.hackathon.backend.dto.security.RegistrationDto;
import com.hackathon.backend.dto.user.UserDto;
import com.hackathon.backend.model.User;
import com.hackathon.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for managing user operations
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Save a user in the database
     *
     * @param user user to be saved
     * @return saved user
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Get a user by login
     *
     * @param login user login
     * @return user with the given login
     * @throws IllegalArgumentException if the user with the given login doesn't exist
     */
    public User getUser(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with login %s doesn't exist", login)));
    }

    /**
     * Check if a user with the given login exists
     *
     * @param login user login
     * @return true if a user with the given login exists, false otherwise
     */
    public boolean userExists(String login) {
        return userRepository.existsUserByLogin(login);
    }

    /**
     * Get a user DTO by login
     *
     * @param login user login
     * @return user DTO with the given login
     * @throws IllegalArgumentException if the user with the given login doesn't exist
     */
    public UserDto getUserDto(String login) {
        return userRepository.findByLogin(login)
                .map(this::mapToUserDto)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with login %s doesn't exist", login)));
    }

    /**
     * Register a new user
     *
     * @param registrationDto DTO for registering a new user
     * @return DTO for the registered user
     * @throws IllegalArgumentException if the user with the given login already exists
     */
    public UserDto registerUser(RegistrationDto registrationDto) {
        if (userExists(registrationDto.getLogin())) {
            throw new IllegalArgumentException(String.format(
                    "User with login %s already exists", registrationDto.getLogin()));
        }
        User user = mapToUser(registrationDto);
        return mapToUserDto(saveUser(user));
    }

    /**
     * Map a registration DTO to a user
     *
     * @param registrationDto registration DTO
     * @return user corresponding to the given registration DTO
     */
    private User mapToUser(RegistrationDto registrationDto) {
        return User.builder()
                .login(registrationDto.getLogin())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .build();
    }


    /**
     * Map a user to a Dto
     *
     * @param user entity
     * @return user corresponding to the given registration DTO
     */
    private UserDto mapToUserDto(User user) {
        return new UserDto(user.getId(), user.getLogin());
    }


    public static UserDto mapToDto(User entity) {
        return new UserDto()
                .setId(entity.getId())
                .setLogin(entity.getLogin());
    }
}
