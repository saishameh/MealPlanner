package com.mealplanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularEquipmentDto {
    private int id;
    private String name;
    private String localizedName;
    private String image;
}