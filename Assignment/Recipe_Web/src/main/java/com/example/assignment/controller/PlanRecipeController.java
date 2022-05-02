/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description: This page is a controller for
 displaying, moving, deleting and change color event on Plan Recipe page
 ******************************************************************************** */


package com.example.assignment.controller;

import com.example.assignment.model.Plan_Recipe;
import com.example.assignment.model.Recipes;
import com.example.assignment.repositories.PlanRepository;
import com.example.assignment.services.UserService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PlanRecipeController {
    @Autowired
    PlanRepository er;
    @Autowired
    UserService userService;


    @RequestMapping(value="/viewplans", method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView calendar(HttpSession session) {
        ModelAndView mv = new ModelAndView("planrecipe");
        String email = (String)session.getAttribute("email");
        ArrayList<String> recipeTitle = new ArrayList<>();
        List<Recipes> userRecipe = userService.findOne(email).getRecipes();
        for(Recipes recipe : userRecipe){
            recipeTitle.add(recipe.getTitle());
        }
        mv.addObject("recipes", recipeTitle);
        return mv;
    }

    @GetMapping("/viewplans/api/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Plan_Recipe> events(HttpSession session,
                                 @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                 @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        String userEmail = (String)session.getAttribute("email");
        return er.findBetween(userEmail, start, end);
    }

    @PostMapping("/viewplans/api/events/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Plan_Recipe createEvent(@RequestBody EventCreateParams params, HttpSession session) {
        String email = (String)session.getAttribute("email");
        Plan_Recipe e = new Plan_Recipe();
        e.setStart(params.start);
        e.setEnd(params.end);
        e.setText(params.text);
        e.setUser(userService.findOne(email));
        er.save(e);

        return e;
    }

    @PostMapping("/viewplans/api/events/move")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Plan_Recipe moveEvent(@RequestBody EventMoveParams params) {

        Plan_Recipe e = er.findById(params.id).get();
        e.setStart(params.start);
        e.setEnd(params.end);
        er.save(e);
        return e;
    }

    @PostMapping("/viewplans/api/events/setColor")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Plan_Recipe setColor(@RequestBody SetColorParams params) {

        Plan_Recipe e = er.findById(params.id).get();
        e.setColor(params.color);
        er.save(e);

        return e;
    }

    @PostMapping("/viewplans/api/events/delete")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    EventDeleteResponse deleteEvent(@RequestBody EventDeleteParams params) {

        er.deleteById(params.id);

        return new EventDeleteResponse() {{
            message = "Deleted";
        }};
    }

    public static class EventDeleteParams {
        public Long id;
    }

    public static class EventDeleteResponse {
        public String message;
    }

    public static class EventCreateParams {
        public LocalDateTime start;
        public LocalDateTime end;
        public String text;
    }

    public static class EventMoveParams {
        public Long id;
        public LocalDateTime start;
        public LocalDateTime end;
    }

    public static class SetColorParams {
        public Long id;
        public String color;
    }
}