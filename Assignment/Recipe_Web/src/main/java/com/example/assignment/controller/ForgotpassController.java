package com.example.assignment.controller;

import com.example.assignment.model.Users;
import com.example.assignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/forgotpassword")
    public String forgotPassword( @ModelAttribute("user")  Users user, Model model)  {
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

}
