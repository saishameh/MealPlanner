package com.mealplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularRecipeDto {
    private int id;
    private String title;
    private String image;
    private String imageType;
    private int servings;
    private int readyInMinutes;
    private String license;
    private String sourceName;
    private String sourceUrl;
    private String spoonacularSourceUrl;
    private int healthScore;
    private double spoonacularScore;
    private double pricePerServing;
    private List<String> dishTypes;
    private List<String> diets;
    private List<String> occasions;
    private String summary;
    private List<SpoonacularIngredientDto> extendedIngredients;
    private List<SpoonacularInstructionDto> analyzedInstructions;
}
