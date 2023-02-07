package com.hackathon.backend.service.dish;

import com.hackathon.backend.dto.dish.difficulty.GetDishDifficultyDto;
import com.hackathon.backend.dto.dish.difficulty.PutDishDifficultyDto;
import com.hackathon.backend.dto.dish.difficulty.DishDifficultyDto;
import com.hackathon.backend.model.DishDifficulty;
import com.hackathon.backend.repository.DishDifficultyRepository;
import com.hackathon.backend.repository.DishRepository;
import com.hackathon.backend.repository.UserRepository;
import com.hackathon.backend.service.dish.DishService;
import com.hackathon.backend.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class DishDifficultyService {

    private final DishDifficultyRepository repository;
    private final DishRepository dishRepository;
    private final UserRepository userRepository;

    public DishDifficultyService(DishDifficultyRepository repository, DishRepository dishRepository,
                                 UserRepository userRepository) {
        this.repository = repository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
    }

    public DishDifficultyDto getDifficulty(GetDishDifficultyDto dto) {
        if (dto.userId() == null || dto.dishId() == null)
            throw new IllegalArgumentException("Missing parameters");

        var dish = dishRepository.findById(dto.dishId()).orElseThrow(IllegalArgumentException::new);
        var user = userRepository.findById(dto.userId()).orElseThrow(IllegalArgumentException::new);

        var difficulty = repository.findDishDifficultyByUserAndDish(user, dish);
        var response = new DishDifficultyDto();

        if (difficulty != null) response = mapToResponseDto(difficulty);

        return response;
    }

    public DishDifficultyDto setDifficulty(PutDishDifficultyDto dto) {
        if (dto.userId() == null || dto.dishId() == null || dto.difficulty() == null)
            throw new IllegalArgumentException("Missing parameters");

        var dish = dishRepository.findById(dto.dishId()).orElseThrow(IllegalArgumentException::new);
        var user = userRepository.findById(dto.userId()).orElseThrow(IllegalArgumentException::new);
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

    public DishDifficultyDto mapToResponseDto(DishDifficulty entity) {
        return new DishDifficultyDto()
                .setId(entity.getId())
                .setUser(UserService.mapToDto(entity.getUser()))
                .setDish(DishService.mapToDto(entity.getDish()))
                .setDifficulty(entity.getDifficulty());
    }
}
