
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description: This page is a controller for displaying index page (login)
 2 account available:
                wynnetran@gmail.com - zzzz (password)
                shitran@gmail.com - zzzz (password)
 ******************************************************************************** */

package com.example.assignment.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("login")
    public String showIndexPage(){
        return"index";
    }

}
