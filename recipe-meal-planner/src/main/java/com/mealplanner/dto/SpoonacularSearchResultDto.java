package com.mealplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularSearchResultDto {
    private List<SpoonacularRecipeDto> results;
    private int offset;
    private int number;
    private int totalResults;
}
