package com.hackathon.backend.model;

import com.hackathon.backend.dto.DishDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String recipe;
    @Column(nullable = false)
    private Double difficulty;
    @Column(nullable = false)
    private Integer votesAmount;
    @Column(nullable = false)
    private String imageUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Dish dish = (Dish) o;
        return id != null && Objects.equals(id, dish.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static Dish fromDishDto(DishDto dishDto) {
        return new Dish()
                .setName(dishDto.getName())
                .setRecipe(dishDto.getRecipe())
                .setDifficulty(dishDto.getDifficulty())
                .setVotesAmount(dishDto.getVotesAmount())
                .setImageUrl(dishDto.getImageUrl());
    }
}