// File: RecipeRepository.java
package com.mealplanner.repository;

import com.mealplanner.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByExternalId(String externalId);
}
