package com.mealplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularStepDto {
    private int number;
    private String step;
    private List<SpoonacularIngredientDto> ingredients;
    private List<SpoonacularEquipmentDto> equipment;
}