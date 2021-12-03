
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1 & 2
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page use as controller for viewing Ingredient and edit it
 ******************************************************************************** */


package com.example.assignment.controller;
import com.example.assignment.AssignmentApplication;
import com.example.assignment.model.Recipes;
import com.example.assignment.services.FavoriteService;
import com.example.assignment.services.RecipeService;
import com.example.assignment.services.UploadFileService;
import com.example.assignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;
@Controller
public class ViewIngredient {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private UploadFileService uploadFileService;
    @Autowired
    private UserService userService;



    @GetMapping("/viewingredient/{id}")
    public  String viewingredient(@PathVariable("id") Long id, HttpSession session, Model model){
        Recipes viewIngredient= recipeService.findOne(id);
        model.addAttribute("recipe", viewIngredient);
        String email = (String)session.getAttribute("email");
        if(Objects.equals(viewIngredient.getUser().getEmail(), email)){
            model.addAttribute("author", true);
        }
        else{
            model.addAttribute("unauthor", true);
        }
        return "Ingredient";
    }

    @GetMapping("/editingredient/{id}")
    public  String edit_ingredient(@PathVariable("id") Long id, Model model){
        Recipes recipe = recipeService.findOne(id);
        model.addAttribute("recipe", recipe);
        return "editIngredient";
    }

    @PostMapping("/editingredient")
    public String edit_ingredient(
            @RequestParam("id") long id,
            @RequestParam("price") double price,
            @ModelAttribute("recipe") @Valid Recipes recipe,
            BindingResult bindingResult,
            HttpSession session,
            Model model) {
        MultipartFile files = recipe.getMultipartFile();
        recipe.setImage("none.png");
        assert files != null;
        if(!files.isEmpty()){
            uploadFileService.UploadFileHandling(files);
            recipe.setImage(files.getOriginalFilename());
        }
        if(bindingResult.hasErrors()) {
            return "editIngredient";
        }
        recipe.setPrice(price);
        String email = (String) session.getAttribute("email");
        recipe.setCreater(userService.findOne(email).getName());
        Recipes updateRecipe = new Recipes(id,
                recipe.getCreater(),
                recipe.getDate(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getIngredient(),
                recipe.getPrice(),
                recipe.getImage()
                );
        recipeService.createRecipe (updateRecipe, userService.findOne(email));
        model.addAttribute("success", true);
        new Thread(AssignmentApplication::restart).start();
        return  "editIngredient";
    }

}
