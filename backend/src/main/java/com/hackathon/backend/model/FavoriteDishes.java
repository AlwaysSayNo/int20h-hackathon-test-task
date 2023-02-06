package com.hackathon.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "favorite_dishes")
public class FavoriteDishes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_favorite_dishes_user_id")
    )
    private User user;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE
    )
    @JoinTable(name = "favorite_dishes_to_dishes",
            joinColumns = @JoinColumn(name = "favorite_dishes_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"favorite_dishes_id", "dish_id"})
    )
    private Set<Dish> dishes = new LinkedHashSet<>();
}
