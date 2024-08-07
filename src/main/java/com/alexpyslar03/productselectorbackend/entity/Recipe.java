package com.alexpyslar03.productselectorbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private boolean is_vegan;
    private int difficulty_level;
    private Long rating;
    private String image;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "product_recipe",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "recipe_id") }
    )
    private Set<Product> products = new HashSet<>();
}
