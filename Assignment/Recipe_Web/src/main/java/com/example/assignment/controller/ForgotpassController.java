
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1 & 2
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page use as controller for forgot password page
 ******************************************************************************** */


package com.example.assignment.controller;

import com.example.assignment.model.Users;
import com.example.assignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class ForgotpassController {
    @Autowired
    private UserService userService;

    @GetMapping("/forgotpassword")
    public String forgotPassword(Model model){
        model.addAttribute("user", new Users());
        return "forgotpassword";
    }

    @GetMapping("/changepassword")
    public String changePassword(Model model){
        model.addAttribute("user", new Users());
        return "changepassword";
    }

    @PostMapping("/forgotpassword")
    public String forgotPassword( @ModelAttribute("user")  Users user, Model model)  {
        if (Objects.equals(user.getEmail(), "") || user.getPassword() == null){
            model.addAttribute("nullEmail", true);
            return "forgotpassword";
        }
        Users updateUser = userService.findOne(user.getEmail());

        if (!Objects.equals(user.getPassword(), user.getRepeatPassword())){
            model.addAttribute("confirmPass", true);
            return "forgotpassword";
        }
        if(!userService.isUserPresent(user.getEmail())) {
            model.addAttribute("noExist", true);
            return "forgotpassword";
        }
        updateUser.setPassword(user.getPassword());
        userService.updateUser(updateUser);
        model.addAttribute("newPassword", true);
        return "index";

    }

    @PostMapping("/changepassword")
    public String changePassword(
            @ModelAttribute("user")  Users user,
            HttpSession session,  Model model,
                                 @RequestParam(name="password", required = false) String password,
                                 @RequestParam(name = "repeatPassword", required = false) String confirmpass
    )  {
        String email = (String) session.getAttribute("email");
        Users updateUser = userService.findOne(email);

        if (Objects.equals(password, "")){
            model.addAttribute("nullPass", true);
            return "changepassword";
        }

        if (!Objects.equals(password, confirmpass)){
            model.addAttribute("error", true);
            return "changepassword";
        }
        updateUser.setPassword(password);
        userService.updateUser(updateUser);
        model.addAttribute("newPassword", true);
        return "changepassword";

    }

}
