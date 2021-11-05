
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is ProfileService, connecting ProfileRepository to ProfileController
 ******************************************************************************** */
package com.example.assignment.services;

import com.example.assignment.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    RecipeRepository recipeRepository;
    public void deleteRecipe(Long id) {
       recipeRepository .deleteById(id);
    }
}
