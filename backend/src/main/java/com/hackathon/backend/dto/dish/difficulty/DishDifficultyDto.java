package com.hackathon.backend.dto.dish.difficulty;

import com.hackathon.backend.dto.DishDto;
import com.hackathon.backend.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DishDifficultyDto {
    private Long id;
    private UserDto user;
    private DishDto dish;
    private Double difficulty;
}
