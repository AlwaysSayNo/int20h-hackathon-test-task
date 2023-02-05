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
@Table(name = "favorite_products")
public class FavoriteProducts {

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
            foreignKey = @ForeignKey(name = "fk_favorite_products_user_id")
    )
    private User user;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE
    )
    @JoinTable(name = "favorite_products_to_products",
            joinColumns = @JoinColumn(name = "favorite_products_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"favorite_products_id", "product_id"})
    )
    private Set<Product> products = new LinkedHashSet<>();

}
