package com.mealplanner.controller;

import com.mealplanner.model.GroceryList;
import com.mealplanner.service.GroceryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/grocery")
@CrossOrigin(origins = "*")
public class GroceryListController {

    private final GroceryService groceryService;

    public GroceryListController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<GroceryList> getGroceryListById(@PathVariable Long id) {
        return groceryService.getGroceryListById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/generate/{mealPlanId}")
    public ResponseEntity<GroceryList> generateGroceryList(@PathVariable Long mealPlanId) {
        return groceryService.generateGroceryList(mealPlanId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/{listId}/items/{itemId}")
    public ResponseEntity<GroceryList> updateGroceryItemStatus(
            @PathVariable Long listId,
            @PathVariable Long itemId,
            @RequestParam boolean purchased) {
        
        return groceryService.updateGroceryItemStatus(listId, itemId, purchased)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}