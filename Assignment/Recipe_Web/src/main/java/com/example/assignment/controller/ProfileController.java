
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a controller that displaying profile page, including
 all user's recipes and user's recipes favorite.
 We can delete recipes and favorite recipes.
 For user's recipes, when we deleted it, it will be disappear on "plan recipe", "search" and "view recipes" page.
 For favorite recipes, when we deleted it, you can like it again in "view recipes" page.
 ******************************************************************************** */


package com.example.assignment.controller;
import com.example.assignment.model.Recipes;
import com.example.assignment.model.Users;
import com.example.assignment.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller

public class ProfileController {

    @Autowired
    UserService userService;
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    ProfileService profileService;
    @Autowired
    PlanService planService;
    @Autowired
    RecipeService recipeService;

    @GetMapping("/profile")
    public  String showProfilePage(HttpSession session, Principal principal, Model model){
        String email = principal.getName();
        Users user = userService.findOne(email);
        user.setRecipeCount(recipeService.findAllUser(user).size());
        user.setLikeCount(favoriteService.findByEmail(email).size());
        model.addAttribute("favorites", favoriteService.findByEmail(email));
        model.addAttribute("user", user);
        model.addAttribute("viewRecipes", true);
        session.setAttribute("username", user.getName());
        session.setAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("/profile")
    public  String deleteRecipe(@RequestParam("id") Long id){
        Recipes deleteRecipe = recipeService.findOne(id);
        profileService.deleteRecipe(id);
        planService.deletePlan(deleteRecipe.getTitle());
        return "redirect:/profile";
    }

    @GetMapping("/viewFavorites")
    public  String viewFavorites(HttpSession session, Model model){
        String email = (String) session.getAttribute("email");
        Users user = userService.findOne(email);
        user.setRecipeCount(recipeService.findAllUser(user).size());
        user.setLikeCount(favoriteService.findByEmail(email).size());
        model.addAttribute("favorites", favoriteService.findByEmail(email));
        model.addAttribute("user", user);

        return "viewFavorites";
    }


    @PostMapping("/deletefavorite")
    public  String deleteFavorite(@RequestParam("id") Integer id){
        Long recipeId = favoriteService.findOne(id).getRecipe_id();
        recipeService.findOne(recipeId).setFavorite_like("0");
        favoriteService.deleteFavorite(id);
        return "redirect:/profile";
    }

}
