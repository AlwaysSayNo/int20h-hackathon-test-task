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
@Table(name = "products_to_dishes")
public class ProductToDish {

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
            foreignKey = @ForeignKey(name = "fk_products_to_dishes_dish_id")
    )
    private Dish dish;

    @ManyToOne(
            optional = false
    )
    @JoinColumn(
            name = "product_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_products_to_dishes_product_id")
    )
    private Product product;

    @Column(nullable = false)
    private String measure;

}
