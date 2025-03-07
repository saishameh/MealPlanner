// Recipe.java
package com.mealplanner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String externalId;
    private String title;
    private String imageUrl;
    private Integer readyInMinutes;
    private Integer servings;
    
    @Column(length = 2000)
    private String summary;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipe_id")
    private List<Ingredient> ingredients = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "recipe_instructions", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(length = 1000)
    private List<String> instructions = new ArrayList<>();
}