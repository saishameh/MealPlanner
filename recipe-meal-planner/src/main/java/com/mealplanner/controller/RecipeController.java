package com.mealplanner.controller;

import com.mealplanner.model.Recipe;
import com.mealplanner.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(
            @RequestParam String query,
            @RequestParam(defaultValue = "10") int number) {
        List<Recipe> recipes = recipeService.searchRecipes(query, number);
        return ResponseEntity.ok(recipes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
}

