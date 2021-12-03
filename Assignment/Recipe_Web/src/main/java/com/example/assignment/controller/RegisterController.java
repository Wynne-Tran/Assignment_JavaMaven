
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page is a controller that displays registration  form and handling add new user
 ******************************************************************************** */

package com.example.assignment.controller;

import com.example.assignment.AssignmentApplication;
import com.example.assignment.model.Users;
import com.example.assignment.services.UploadFileService;
import com.example.assignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.*;
import java.util.Objects;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    ServletContext context;
    @Autowired
    UploadFileService uploadFileService;


    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new Users());
        return "registration_form";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("user") @Valid  Users user,
            BindingResult bindingResult, Model model) throws IOException {
        MultipartFile files = user.getMultipartFile();
        user.setImage("none.png");
        assert files != null;
        if(!files.isEmpty()){
            uploadFileService.UploadFileHandling(files);
            user.setImage(files.getOriginalFilename());
        }

        if (!Objects.equals(user.getPassword(), user.getRepeatPassword())){
            model.addAttribute("confirmPass", true);
            return "registration_form";
        }
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            return "registration_form";
        }
        if(userService.isUserPresent(user.getEmail())) {
            model.addAttribute("exist", true);
            return "registration_form";
        }
        userService.createUser(user);
        model.addAttribute("showUsername", true);
        new Thread(AssignmentApplication::restart).start();

        return "index";
    }

}
