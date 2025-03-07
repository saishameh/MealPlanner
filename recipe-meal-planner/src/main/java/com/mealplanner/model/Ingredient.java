package com.mealplanner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String amount;
    private String unit;
    
    public Ingredient(String name, String amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }
}
