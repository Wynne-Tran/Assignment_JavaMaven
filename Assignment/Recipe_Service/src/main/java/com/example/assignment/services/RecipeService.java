
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is RecipeService, connecting RecipeRepository to RecipeController
 ******************************************************************************** */
package com.example.assignment.services;

import com.example.assignment.model.Recipes;
import com.example.assignment.model.Users;
import com.example.assignment.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public void createRecipe(Recipes recipe, Users user){
        recipe.setUser(user);
        recipeRepository.save(recipe);
    }



    public List<Recipes> findByTitle(String title) {
        // TODO Auto-generated method stub
        return  recipeRepository.findByTitleLike("%"+title+"%");
    }

    public List<Recipes> findAllUser(Users user){return recipeRepository.findByUser(user);}

    public Recipes findOne(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public List<Recipes> findAll(){
            return recipeRepository.findAll(Sort.by(Sort.Direction.ASC, "date"));
    }

    public void deleteRecipe(Long id) {

        recipeRepository .deleteById(id);
    }


}
