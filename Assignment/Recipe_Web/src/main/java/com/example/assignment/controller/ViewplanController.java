package com.example.assignment.controller;

import com.example.assignment.model.Plan_Recipe;
import com.example.assignment.model.Recipes;
import com.example.assignment.model.Users;
import com.example.assignment.repositories.PlanRepository;
import com.example.assignment.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ViewplanController {
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
    @Autowired
    PlanRepository er;


    @GetMapping("/planrecipe")
    public  String viewFavorites(HttpSession session, Model model){
        String email = (String) session.getAttribute("email");
        Users user = userService.findOne(email);
        user.setRecipeCount(recipeService.findAllUser(user).size());
        user.setLikeCount(favoriteService.findByEmail(email).size());
        model.addAttribute("user", user);

        List<Plan_Recipe> listPlan = planService.findByEmail(email);
        model.addAttribute("listPlan",listPlan);
        return "viewplans";
    }

    @PostMapping("/planrecipe")
    public  String deleteEvent(Long id) {
        planService.deletePlan(id);
        return "redirect:/planrecipe";
    }


    @GetMapping("/editplan/{id}")
    public  String editPlan(@PathVariable("id") Long id, Model model, HttpSession session){
        String email = (String) session.getAttribute("email");
        List<Recipes> recipes =  recipeService.findAllUser(userService.findOne(email));
        model.addAttribute("recipeTitle", recipes);
        model.addAttribute("editPlan", er.findById(id));

        return "editPlan";
    }

    @PostMapping("/editplan")
    public  String editPlan(HttpSession session,
                            @RequestParam("id")  Long id,
                            @RequestParam("text")  String text,
                            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
                            ) {
        String email = (String) session.getAttribute("email");
        Plan_Recipe editPlan = new Plan_Recipe (id, text, userService.findOne(email), start, end);
        er.save(editPlan);
        return "redirect:/viewplans";
    }

    @GetMapping("/addplan")
    public  String addPlan( HttpSession session, Model model){
        String email = (String) session.getAttribute("email");
        List<Recipes> recipes =  recipeService.findAllUser(userService.findOne(email));
        if(recipes.size() == 0){
            model.addAttribute("nullList", true);
        }
        model.addAttribute("recipeTitle", recipes);
        model.addAttribute("Plan_Recipe", new Plan_Recipe());
        return "addPlan";
    }

    @PostMapping("/addplan")
    public  String addPlan(Model model, HttpSession session,
                           @RequestParam("text")  String text,
                           @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                           @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
                           ){
        String email = (String) session.getAttribute("email");
        Plan_Recipe newPlan = new Plan_Recipe (text, userService.findOne(email), start, end);
        er.save(newPlan);
        model.addAttribute("success", true);
        return "redirect:/viewplans";
    }

}
