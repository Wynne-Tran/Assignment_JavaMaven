
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a controller that displaying create recipe form and handling add new recipe.
 ******************************************************************************** */


package com.example.assignment.controller;

import com.example.assignment.model.Recipes;
import com.example.assignment.services.RecipeService;
import com.example.assignment.services.UploadFileService;
import com.example.assignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserService userService;
    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/createrecipe")
    public String recipeForm( Model model) {
        model.addAttribute("recipe", new Recipes());
        return "createrecipe";
    }

    @PostMapping("/createrecipe")
    public String createRecipe(
            @ModelAttribute ("recipe") @Valid Recipes recipe, Model model,
            BindingResult bindingResult,
            HttpSession session) {

        MultipartFile files = recipe.getMultipartFile();
        recipe.setImage("none.png");
        assert files != null;
        if(!files.isEmpty()){
            uploadFileService.UploadFileHandling(files);
            recipe.setImage(files.getOriginalFilename());
        }

        if(bindingResult.hasErrors()) {
            return "createrecipe";
        }

        String email = (String) session.getAttribute("email");
        recipe.setCreater(userService.findOne(email).getName());
        recipeService.createRecipe(recipe, userService.findOne(email));
        model.addAttribute("success", true);
        return  "createrecipe";
    }
}
