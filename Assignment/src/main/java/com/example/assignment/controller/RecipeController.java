package com.example.assignment.controller;

import com.example.assignment.model.Recipes;
import com.example.assignment.services.RecipeService;
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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserService userService;

    @GetMapping("/createrecipe")
    public String recipeForm( Model model) {
        model.addAttribute("recipe", new Recipes());
        return "createrecipe";
    }

    @PostMapping("/createrecipe")
    public String createRecipe(
            @ModelAttribute ("recipe") @Valid Recipes recipe, Model model,
            BindingResult bindingResult,
            HttpSession session)  throws IOException {
        MultipartFile files = recipe.getMultipartFile();
        recipe.setImage("none.png");
        if(!files.isEmpty()){
            try {
                String fileName = files.getOriginalFilename();
                String dirLocation ="Assignment/src/main/resources/static/image/";
                File file = new File(dirLocation);
                if(!file.exists()) {
                    file.mkdir();
                }
                recipe.setImage(fileName);
                byte[] bytes = files.getBytes();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(dirLocation+new File(fileName)));
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
