package com.hackathon.backend.service.dish;

import com.hackathon.backend.dto.dish.difficulty.GetDishDifficultyDto;
import com.hackathon.backend.dto.dish.difficulty.PutDishDifficultyDto;
import com.hackathon.backend.dto.dish.difficulty.DishDifficultyDto;
import com.hackathon.backend.model.DishDifficulty;
import com.hackathon.backend.model.User;
import com.hackathon.backend.repository.DishDifficultyRepository;
import com.hackathon.backend.repository.DishRepository;
import com.hackathon.backend.repository.UserRepository;
import com.hackathon.backend.service.user.UserService;
import org.springframework.stereotype.Service;

/**
 * DishDifficultyService class is used to perform CRUD operations related to dish difficulties for a user.
 * It contains methods to get and set difficulty for a dish for a user.
 */
@Service
public class DishDifficultyService {

    private final DishDifficultyRepository repository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public DishDifficultyService(DishDifficultyRepository repository, DishRepository dishRepository,
                                 UserRepository userRepository, UserService userService) {
        this.repository = repository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Get the difficulty for a specific user and dish.
     *
     * @param userLogin The login of the user
     * @param dto The GetDishDifficultyDto instance which holds the userId and dishId
     * @return The DishDifficultyDto instance which represents the difficulty for the specific user and dish
     * @throws IllegalArgumentException if userId or dishId is missing in the GetDishDifficultyDto instance
     */
    public DishDifficultyDto getDifficultyForUser(String userLogin, GetDishDifficultyDto dto) {
        if (dto.userId() == null || dto.dishId() == null)
            throw new IllegalArgumentException("Missing parameters");

        var dish = dishRepository.findById(dto.dishId()).orElseThrow(IllegalArgumentException::new);
        var user = userRepository.findById(dto.userId()).orElseThrow(IllegalArgumentException::new);

        verifyUser(userLogin, user);

        var dbUser = userService.getUser(userLogin);
        verifyUser(userLogin, user);

        var difficulty = repository.findDishDifficultyByUserAndDish(user, dish);
        var response = new DishDifficultyDto();

        if (difficulty != null) response = mapToResponseDto(difficulty);

        return response;
    }

    /**
     * Set the difficulty for a specific user and dish.
     *
     * @param userLogin The login of the user
     * @param dto The PutDishDifficultyDto instance which holds the userId, dishId and difficulty
     * @return The DishDifficultyDto instance which represents the updated difficulty for the specific user and dish
     * @throws IllegalArgumentException if userId, dishId or difficulty is missing in the PutDishDifficultyDto instance
     */
    public DishDifficultyDto setDifficultyForUser(String userLogin, PutDishDifficultyDto dto) {
        if (dto.userId() == null || dto.dishId() == null || dto.difficulty() == null)
            throw new IllegalArgumentException("Missing parameters");

        var dish = dishRepository.findById(dto.dishId()).orElseThrow(IllegalArgumentException::new);
        var user = userRepository.findById(dto.userId()).orElseThrow(IllegalArgumentException::new);

        verifyUser(userLogin, user);

        var dishDifficulty = repository.findDishDifficultyByUserAndDish(user, dish);

        var newUserDifficulty = dto.difficulty();
        var newSum = dish.getDifficulty() * dish.getVotesAmount() + newUserDifficulty;

        if (dishDifficulty != null) {
            newSum -= dishDifficulty.getDifficulty();

            dishDifficulty.setDifficulty(dto.difficulty());
        }
        else {
            dishDifficulty = new DishDifficulty()
                    .setDifficulty(newUserDifficulty)
                    .setUser(user)
                    .setDish(dish);

            dish.setVotesAmount(dish.getVotesAmount() + 1);
        }

        dish.setDifficulty(newSum / dish.getVotesAmount());

        dish = dishRepository.save(dish);
        dishDifficulty = repository.save(dishDifficulty).setDish(dish);

        return mapToResponseDto(dishDifficulty);
    }

    /**
     * Verify if the user is valid for the dish difficulty.
     *
     * @param login The login of the user
     * @param user The User instance
     * @throws IllegalArgumentException if the user is illegal for this dish difficulty
     */
    private void verifyUser(String login, User user) {
        var dbUser = userService.getUser(login);
        if (!dbUser.getLogin().equals(user.getLogin()))
            throw new IllegalArgumentException("Illegal user for this dish difficulty");
    }

    /**
     * Map the DishDifficulty entity to the DishDifficultyDto instance.
     *
     * @param entity The DishDifficulty instance
     * @return The DishDifficultyDto instance
     */
    public DishDifficultyDto mapToResponseDto(DishDifficulty entity) {
        return new DishDifficultyDto()
                .setId(entity.getId())
                .setUser(UserService.mapToDto(entity.getUser()))
                .setDish(DishService.mapToDto(entity.getDish()))
                .setDifficulty(entity.getDifficulty());
    }
}
