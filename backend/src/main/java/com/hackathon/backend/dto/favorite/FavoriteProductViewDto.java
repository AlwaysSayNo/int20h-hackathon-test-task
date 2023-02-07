package com.hackathon.backend.dto.favorite;

import com.hackathon.backend.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteProductViewDto extends AbstractFavoriteDto {
    private List<ProductDto> products;
}

