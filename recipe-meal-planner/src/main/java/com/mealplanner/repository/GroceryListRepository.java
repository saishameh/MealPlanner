// File: GroceryListRepository.java
package com.mealplanner.repository;

import com.mealplanner.model.GroceryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroceryListRepository extends JpaRepository<GroceryList, Long> {
    List<GroceryList> findByMealPlanId(Long mealPlanId);
}