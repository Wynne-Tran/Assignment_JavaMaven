
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a controller that displays registration  form and handling add new user
 ******************************************************************************** */


package com.example.assignment.controller;

import com.example.assignment.model.Favorite;
import com.example.assignment.model.Recipes;
import com.example.assignment.services.FavoriteService;
import com.example.assignment.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
public class SearchController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/search")
    public String listRecipes (HttpSession session, Model model, @RequestParam(defaultValue = "") String title){
        String email = (String)session.getAttribute("email");
        List<Recipes> fixLike = recipeService.findAll();
        for(Recipes recipe  : fixLike){
            if(Objects.equals(recipe.getUser().getEmail(), email)){
                recipe.setFavorite_like("1");
            }
        }
        model.addAttribute("recipes", recipeService.findByTitle(title));
        return "Search";
    }


    @PostMapping("/search")
    public  String addToFavorite(Model model, HttpSession session, @RequestParam("id") Long id){
        String email = (String)session.getAttribute("email");
        Favorite newFavorite = new Favorite(email, id, recipeService.findOne(id));
        recipeService.findOne(id).setFavorite_like("2");
        favoriteService.addToFavorite(newFavorite);
        model.addAttribute("recipes", recipeService.findAll());
        return "Search";
    }


}
