package com.hackathon.backend.dto.favorite;

import com.hackathon.backend.dto.DishDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDishViewDto extends AbstractFavoriteDto {
    private List<DishDto> dishList;
}
