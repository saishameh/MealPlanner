package com.mealplanner.controller;

import com.mealplanner.model.MealPlan;
import com.mealplanner.model.MealPlanItem;
import com.mealplanner.service.MealPlanService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/mealplans")
@CrossOrigin(origins = "*")
public class MealPlanController {

    private final MealPlanService mealPlanService;

    public MealPlanController(MealPlanService mealPlanService) {
        this.mealPlanService = mealPlanService;
    }
    
    @GetMapping
    public ResponseEntity<List<MealPlan>> getAllMealPlans() {
        return ResponseEntity.ok(mealPlanService.getAllMealPlans());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MealPlan> getMealPlanById(@PathVariable Long id) {
        return mealPlanService.getMealPlanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<MealPlan> createMealPlan(@RequestBody MealPlan mealPlan) {
        return ResponseEntity.ok(mealPlanService.createMealPlan(mealPlan));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<MealPlan> updateMealPlan(@PathVariable Long id, @RequestBody MealPlan mealPlan) {
        return mealPlanService.updateMealPlan(id, mealPlan)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMealPlan(@PathVariable Long id) {
        mealPlanService.deleteMealPlan(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/recipes")
    public ResponseEntity<MealPlan> addRecipeToMealPlan(
            @PathVariable Long id,
            @RequestParam String recipeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam MealPlanItem.MealType mealType) {
        
        return mealPlanService.addRecipeToMealPlan(id, recipeId, date, mealType)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

