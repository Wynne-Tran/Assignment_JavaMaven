
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a repository database of Recipes
 ******************************************************************************** */

package com.example.assignment.repositories;

import com.example.assignment.model.Recipes;
import com.example.assignment.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository <Recipes, Long> {
    List<Recipes> findByUser(Users user);
    List<Recipes> findByTitleLike(String title);
}
