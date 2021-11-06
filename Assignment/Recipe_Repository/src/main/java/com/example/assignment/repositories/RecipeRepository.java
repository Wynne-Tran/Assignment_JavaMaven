package com.example.assignment.repositories;

import com.example.assignment.model.Recipes;
import com.example.assignment.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository <Recipes, Long> {
    List<Recipes> findByUser(Users user);
    List<Recipes> findByTitleLike(String title);
}
