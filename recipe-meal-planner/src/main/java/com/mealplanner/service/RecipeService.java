
package com.mealplanner.service;

import com.mealplanner.dto.SpoonacularRecipeDto;
import com.mealplanner.dto.SpoonacularSearchResultDto;
import com.mealplanner.model.Ingredient;
import com.mealplanner.model.Recipe;
import com.mealplanner.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RestTemplate restTemplate;
    private final RecipeRepository recipeRepository;
    
    @Value("${spoonacular.api.key}")
    private String apiKey;
    
    @Value("${spoonacular.api.baseUrl:https://api.spoonacular.com}")
    private String baseUrl;

    public RecipeService(RestTemplate restTemplate, RecipeRepository recipeRepository) {
        this.restTemplate = restTemplate;
        this.recipeRepository = recipeRepository;
    }
    
    public List<Recipe> searchRecipes(String query, int number) {
        String url = baseUrl + "/recipes/complexSearch?apiKey=" + apiKey 
                + "&query=" + query 
                + "&number=" + number
                + "&addRecipeInformation=true";
        
        SpoonacularSearchResultDto result = restTemplate.getForObject(url, SpoonacularSearchResultDto.class);
        
        if (result == null || result.getResults() == null) {
            return Collections.emptyList();
        }
        
        return result.getResults().stream()
                .map(this::convertToRecipe)
                .collect(Collectors.toList());
    }
    
    public Recipe getRecipeById(String id) {
        // First check if we already have this recipe in our database
        Optional<Recipe> existingRecipe = recipeRepository.findByExternalId(id);
        if (existingRecipe.isPresent()) {
            return existingRecipe.get();
        }
        
        // If not, fetch from API
        String url = baseUrl + "/recipes/" + id + "/information?apiKey=" + apiKey;
        SpoonacularRecipeDto recipeDto = restTemplate.getForObject(url, SpoonacularRecipeDto.class);
        
        if (recipeDto == null) {
            return null;
        }
        
        Recipe recipe = convertDetailedRecipe(recipeDto);
        return recipeRepository.save(recipe);
    }
    
    private Recipe convertToRecipe(SpoonacularRecipeDto dto) {
        Recipe recipe = new Recipe();
        recipe.setExternalId(String.valueOf(dto.getId()));
        recipe.setTitle(dto.getTitle());
        recipe.setImageUrl(dto.getImage());
        recipe.setReadyInMinutes(dto.getReadyInMinutes());
        recipe.setServings(dto.getServings());
        recipe.setSummary(dto.getSummary());
        
        // For search results, we may not have detailed information
        return recipe;
    }
    
    private Recipe convertDetailedRecipe(SpoonacularRecipeDto dto) {
        Recipe recipe = convertToRecipe(dto);
        
        // Add ingredients
        if (dto.getExtendedIngredients() != null) {
            List<Ingredient> ingredients = dto.getExtendedIngredients().stream()
                    .map(i -> new Ingredient(
                            i.getName(),
                            String.valueOf(i.getAmount()),
                            i.getUnit()))
                    .collect(Collectors.toList());
            recipe.setIngredients(ingredients);
        }
        
        // Add instructions
        if (dto.getAnalyzedInstructions() != null && !dto.getAnalyzedInstructions().isEmpty()) {
            List<String> steps = dto.getAnalyzedInstructions().get(0).getSteps().stream()
                    .map(s -> s.getStep())
                    .collect(Collectors.toList());
            recipe.setInstructions(steps);
        }
        
        return recipe;
    }
}
