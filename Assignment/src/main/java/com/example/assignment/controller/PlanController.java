package com.example.assignment.controller;
import com.example.assignment.model.Users;
import com.example.assignment.services.RecipeService;
import com.example.assignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class PlanController {
    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;

    @GetMapping("/planrecipe")
    public String toDoList (Model model, HttpSession session,
                            Principal principal)
    {
        String email = principal.getName();
        Users user = userService.findOne(email);
        session.setAttribute("email", user.getEmail());
        return "planrecipe";

    }
}
