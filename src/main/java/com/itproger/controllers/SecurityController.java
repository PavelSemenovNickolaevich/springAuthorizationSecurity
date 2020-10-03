package com.itproger.controllers;

import com.itproger.models.User;
import com.itproger.repo.ReviewRepository;
import com.itproger.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;


@Controller
public class SecurityController {



    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdminPage(Model model, Principal principal) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin";
    }




    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/admin/user-{id}")
    public String getAdminInfo(Model model, @PathVariable(value = "id") long userId, Principal principal) {
//        User user = userRepository.findById(userId);
        User user = userRepository.findByUsername(principal.getName());

//        model.addAttribute("username", user.getUsername());
//        model.addAttribute("email", user.getEmail());
//        model.addAttribute("role", user.getRoles());
        model.addAttribute("user", user.getReviews());
        return "admin-info";
    }
}



