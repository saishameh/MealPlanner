
// File: MealPlanService.java
package com.mealplanner.service;

import com.mealplanner.model.MealPlan;
import com.mealplanner.model.MealPlanItem;
import com.mealplanner.model.Recipe;
import com.mealplanner.repository.MealPlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final RecipeService recipeService;

    public MealPlanService(MealPlanRepository mealPlanRepository, RecipeService recipeService) {
        this.mealPlanRepository = mealPlanRepository;
        this.recipeService = recipeService;
    }
    
    public List<MealPlan> getAllMealPlans() {
        return mealPlanRepository.findAll();
    }
    
    public Optional<MealPlan> getMealPlanById(Long id) {
        return mealPlanRepository.findById(id);
    }
    
    public MealPlan createMealPlan(MealPlan mealPlan) {
        return mealPlanRepository.save(mealPlan);
    }
    
    public Optional<MealPlan> updateMealPlan(Long id, MealPlan updatedMealPlan) {
        if (!mealPlanRepository.existsById(id)) {
            return Optional.empty();
        }
        
        updatedMealPlan.setId(id);
        return Optional.of(mealPlanRepository.save(updatedMealPlan));
    }
    
    public void deleteMealPlan(Long id) {
        mealPlanRepository.deleteById(id);
    }
    
    public Optional<MealPlan> addRecipeToMealPlan(Long mealPlanId, String recipeId, LocalDate date, MealPlanItem.MealType mealType) {
        Optional<MealPlan> mealPlanOpt = mealPlanRepository.findById(mealPlanId);
        if (!mealPlanOpt.isPresent()) {
            return Optional.empty();
        }
        
        Recipe recipe = recipeService.getRecipeById(recipeId);
        if (recipe == null) {
            return Optional.empty();
        }
        
        MealPlan mealPlan = mealPlanOpt.get();
        MealPlanItem mealPlanItem = new MealPlanItem();
        mealPlanItem.setDate(date);
        mealPlanItem.setMealType(mealType);
        mealPlanItem.setRecipe(recipe);
        
        mealPlan.getMealItems().add(mealPlanItem);
        return Optional.of(mealPlanRepository.save(mealPlan));
    }
} 
