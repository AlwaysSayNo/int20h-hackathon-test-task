package com.hackathon.backend.dto.favorite;

import com.hackathon.backend.dto.dish.DishDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDishViewDto extends AbstractFavoriteDto {
    private List<DishDto> dishList;
}
