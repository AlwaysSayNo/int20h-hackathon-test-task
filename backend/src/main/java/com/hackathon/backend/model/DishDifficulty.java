package com.hackathon.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "dish_difficulty")
public class DishDifficulty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;


    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "dish_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_dish_difficulty_dish_id")
    )
    private Dish dish;

    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_dish_difficulty_user_id")
    )
    private User user;

    @Column(nullable = false)
    private Double difficulty;

}