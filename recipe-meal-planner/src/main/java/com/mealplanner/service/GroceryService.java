
// File: GroceryService.java
package com.mealplanner.service;

import com.mealplanner.model.*;
import com.mealplanner.repository.GroceryListRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class GroceryService {

    private final GroceryListRepository groceryListRepository;
    private final MealPlanService mealPlanService;

    public GroceryService(GroceryListRepository groceryListRepository, MealPlanService mealPlanService) {
        this.groceryListRepository = groceryListRepository;
        this.mealPlanService = mealPlanService;
    }
    
    public Optional<GroceryList> generateGroceryList(Long mealPlanId) {
        Optional<MealPlan> mealPlanOpt = mealPlanService.getMealPlanById(mealPlanId);
        if (!mealPlanOpt.isPresent()) {
            return Optional.empty();
        }
        
        MealPlan mealPlan = mealPlanOpt.get();
        GroceryList groceryList = new GroceryList();
        groceryList.setName("Grocery List for " + mealPlan.getName());
        groceryList.setCreatedDate(LocalDate.now());
        groceryList.setMealPlan(mealPlan);
        
        // Group ingredients by name for consolidation
        Map<String, GroceryItem> groceryItemMap = new HashMap<>();
        
        for (MealPlanItem mealItem : mealPlan.getMealItems()) {
            Recipe recipe = mealItem.getRecipe();
            for (Ingredient ingredient : recipe.getIngredients()) {
                String key = ingredient.getName().toLowerCase();
                
                if (groceryItemMap.containsKey(key)) {
                    // For simplicity, we're just collecting unique ingredients
                    // In a real app, you'd want to sum quantities which is more complex
                    continue;
                }
                
                GroceryItem groceryItem = new GroceryItem(
                        ingredient.getName(),
                        ingredient.getAmount(),
                        ingredient.getUnit()
                );
                groceryItemMap.put(key, groceryItem);
            }
        }
        
        groceryList.getItems().addAll(groceryItemMap.values());
        return Optional.of(groceryListRepository.save(groceryList));
    }
    
    public Optional<GroceryList> getGroceryListById(Long id) {
        return groceryListRepository.findById(id);
    }
    
    public Optional<GroceryList> updateGroceryItemStatus(Long listId, Long itemId, boolean purchased) {
        Optional<GroceryList> groceryListOpt = groceryListRepository.findById(listId);
        if (!groceryListOpt.isPresent()) {
            return Optional.empty();
        }
        
        GroceryList groceryList = groceryListOpt.get();
        boolean updated = false;
        
        for (GroceryItem item : groceryList.getItems()) {
            if (item.getId().equals(itemId)) {
                item.setPurchased(purchased);
                updated = true;
                break;
            }
        }
        
        if (updated) {
            return Optional.of(groceryListRepository.save(groceryList));
        }
        
        return Optional.empty();
    }
}