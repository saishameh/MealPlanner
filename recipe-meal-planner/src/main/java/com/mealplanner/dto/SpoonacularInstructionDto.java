package com.mealplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularInstructionDto {
    private String name;
    private List<SpoonacularStepDto> steps;
}