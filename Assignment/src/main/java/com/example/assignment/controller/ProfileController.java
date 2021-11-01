package com.example.assignment.controller;

import com.example.assignment.model.Users;
import com.example.assignment.services.FavoriteService;
import com.example.assignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    UserService userService;
    @Autowired
    FavoriteService favoriteService;

    @GetMapping("/profile")
    public  String showProfilePage(HttpSession session, Principal principal, Model model){
        String email = principal.getName();
        Users user = userService.findOne(email);
        model.addAttribute("favorites", favoriteService.findByEmail(email));
        session.setAttribute("userRecipe", user.getRecipes());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("username", user.getName());
        session.setAttribute("image", user.getImage());
        return "profile";
    }

}
