
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is FavoriteService, connecting FavoriteRepository to FavoriteController
 ******************************************************************************** */
package com.example.assignment.services;

import com.example.assignment.model.Favorite;
import com.example.assignment.model.Recipes;
import com.example.assignment.repositories.FavoriteRepository;
import com.example.assignment.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private  FavoriteRepository favoriteRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    public void addToFavorite(Favorite favorite){
            favoriteRepository.save(favorite);
    }
    public void deleteFavorite(Integer id){
        favoriteRepository.deleteById(id);
    }
    public Favorite findOne(Integer id) {
        return favoriteRepository.findById(id).orElse(null);
    }

    public Favorite findFavId(Long id, String email) {
        return favoriteRepository.findFavoriteByRecipe_idAndUser_email(id, email);
    }

    public List<Favorite> findByEmail(String email) {
        // TODO Auto-generated method stub
        return favoriteRepository.findByUserEmail(email);
    }

}
