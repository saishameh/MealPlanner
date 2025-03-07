package com.mealplanner.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealPlanItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    private MealType mealType;
    
    @ManyToOne
    private Recipe recipe;
    
    public enum MealType {
        BREAKFAST, LUNCH, DINNER, SNACK
    }
}

