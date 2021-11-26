package com.example.assignment.controller;

import com.example.assignment.model.Shopping_Cart;
import com.example.assignment.model.Users;
import com.example.assignment.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ShoppingController {

    @Autowired
    UserService userService;
    @Autowired
    RecipeService recipeService;
    @Autowired
    ShoppingService shoppingService;
    @Autowired
    FavoriteService favoriteService;

    @Autowired
    private CsvExportService csvExportService;
    public void RecipeController(CsvExportService csvExportService) {
        this.csvExportService = csvExportService;
    }

    @GetMapping("/view_cart")
    public  String viewCart(HttpSession session, Model model){
        String email = (String) session.getAttribute("email");
        Users user = userService.findOne(email);
        user.setRecipeCount(recipeService.findAllUser(user).size());
        user.setLikeCount(favoriteService.findByEmail(email).size());
        user.setShoppingCount(shoppingService.findByEmail(email).size());
        model.addAttribute("carts", shoppingService.findByEmail(email));
        model.addAttribute("user", user);
        session.setAttribute("cart",user.getShoppingCount());
        return "view_cart";
    }

    @PostMapping("/addCart")
    public  String addCart(@RequestParam("id") Long id,  HttpSession session, Model model){
        String email = (String)session.getAttribute("email");
        Shopping_Cart newCart = new Shopping_Cart(email, id, recipeService.findOne(id));
        shoppingService.addToCart(newCart);
        model.addAttribute("recipes", recipeService.findAll());
        return "redirect:/viewrecipe";
    }

    @PostMapping("/deleteCart")
    public  String deleteCart(@RequestParam("id") Integer id){
        shoppingService.deleteCart(id);
        return "redirect:/view_cart";
    }

    @RequestMapping(path = "/shoppingcart")
    public void getAllListInCsv(HttpServletResponse servletResponse, HttpSession session) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"shoppingcart.csv\"");
        String email = (String) session.getAttribute("email");
        csvExportService.writeToCsv(servletResponse.getWriter(), email);
    }
}
