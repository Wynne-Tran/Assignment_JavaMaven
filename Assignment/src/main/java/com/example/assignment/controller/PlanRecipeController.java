package com.example.assignment.controller;

import com.example.assignment.model.Plan_Recipe;
import com.example.assignment.model.Recipes;
import com.example.assignment.model.Users;
import com.example.assignment.repositories.PlanRepository;
import com.example.assignment.services.RecipeService;
import com.example.assignment.services.UserService;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RestController
public class PlanRecipeController {
    @Autowired
    PlanRepository er;
    @Autowired
    UserService userService;


    @RequestMapping(value="/planrecipe", method= RequestMethod.GET)
    @ResponseBody
    public ModelAndView calendar(HttpSession session, Model model) {
        ModelAndView mv = new ModelAndView();
        String email = (String)session.getAttribute("email");
        mv.addObject("recipes", userService.findOne(email).getRecipes());
        System.out.println(userService.findOne(email).getRecipes());
        return mv;
    }

    @GetMapping("/planrecipe/api/events")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    Iterable<Plan_Recipe> events(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return er.findBetween(start, end);
    }

    @PostMapping("/planrecipe/api/events/create")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Plan_Recipe createEvent(@RequestBody EventCreateParams params, HttpSession session) {
        String email = (String)session.getAttribute("email");
        Plan_Recipe e = new Plan_Recipe();
        e.setStart(params.start);
        e.setEnd(params.end);
        e.setDescription(params.text);
       e.setUser(userService.findOne(email));
        er.save(e);

        return e;
    }

    @PostMapping("/planrecipe/api/events/move")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Plan_Recipe moveEvent(@RequestBody EventMoveParams params) {

        Plan_Recipe e = er.findById(params.id).get();
        e.setStart(params.start);
        e.setEnd(params.end);
        er.save(e);

        return e;
    }

    @PostMapping("/planrecipe/api/events/setColor")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Transactional
    Plan_Recipe setColor(@RequestBody SetColorParams params) {

        Plan_Recipe e = er.findById(params.id).get();
        e.setColor(params.color);
        er.save(e);

        return e;
    }

    @PostMapping("/planrecipe/api/events/delete")
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
