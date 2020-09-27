package com.itproger.controllers;

import com.itproger.models.Review;
import com.itproger.models.User;
import com.itproger.repo.ReviewRepository;
import com.itproger.repo.UserRepository;
import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Controller
public class SecurityController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/admin")
    public String admin(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin";

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/admin/user-{id}")
    public String getAdminInfo(Model model, @PathVariable(value = "id") long userId) {
        User user = userRepository.findById(userId);
        user.getReviews();
        model.addAttribute("user", user.getReviews());
        return "admin-info";
    }
}



