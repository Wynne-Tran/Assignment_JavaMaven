package com.example.assignment.controller;

import com.example.assignment.AssignmentApplication;
import com.example.assignment.model.Users;
import com.example.assignment.repositories.UserRepository;
import com.example.assignment.services.FavoriteService;
import com.example.assignment.services.ProfileService;
import com.example.assignment.services.UploadFileService;
import com.example.assignment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class EditProfileController {
    @Autowired
    UserService userService;
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    ProfileService profileService;
    @Autowired
    UploadFileService uploadFileService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/editprofile")
    public  String editProfile(HttpSession session, Model model){
        String email = (String) session.getAttribute("email");
        Users user = userService.findOne(email);
        model.addAttribute("user", user);
        return "editprofile";
    }

    @PostMapping("/editprofile")
    public  String editProfilePage(
    @RequestParam(value = "name") String username,
    @RequestParam(value = "jobTitle") String jobTitle,
    @RequestParam(value = "about") String about,
    @RequestPart(name = "image", required = false) MultipartFile files,
    HttpSession session, Model model
       ) throws IOException {
        String email = (String) session.getAttribute("email");
        Users user = userService.findOne(email);
        if(!files.isEmpty()){
            uploadFileService.UploadFileHandling(files);
            user.setImage(files.getOriginalFilename());
        }
        user.setName(username);
        user.setJobTitle(jobTitle);
        user.setAbout(about);
        userRepository.save(user);
        model.addAttribute("done", true);
        new Thread(AssignmentApplication::restart).start();
        return "editProfile";
    }
}
