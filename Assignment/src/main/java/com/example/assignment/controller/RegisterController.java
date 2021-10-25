package com.example.assignment.controller;

import com.example.assignment.model.Users;
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
        if(!files.isEmpty()){
            try {
                String fileName = files.getOriginalFilename();
                String dirLocation ="Assignment/src/main/resources/static/image/";
                File file = new File(dirLocation);
                if(!file.exists()) {
                    file.mkdir();
                }
                user.setImage(fileName);
                byte[] bytes = files.getBytes();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(dirLocation+new File(fileName)));
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
        return "index";
    }

}
