package com.itproger.controllers;

import com.itproger.models.Review;
import com.itproger.models.Role;
import com.itproger.models.User;
import com.itproger.repo.ReviewRepository;
import com.itproger.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

@Controller
public class MainController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        model.put("name", "World");
        return "home";
    }

    @GetMapping("/about")
    public String about(Map<String, Object> model) {
        model.put("title", "Страница про нас");
        return "about";
    }

    @GetMapping("/reviews")
    public String reviews(Map<String, Object> model) {
        Iterable<Review> reviews = reviewRepository.findAll();
        model.put("title", "Страница с отзывами");
        model.put("reviews", reviews);
        return "reviews";
    }

    @PostMapping("/reviews-add")
    public String reviewsAdd(@RequestParam String title, @RequestParam String text, Map<String, Object> model) {
        Review review = new Review(title, text);
        reviewRepository.save(review);

        return "redirect:/reviews";
    }

    @GetMapping("/reviews/{id}")
    public String reviewInfo(@PathVariable(value = "id") long reviewId, Map<String, Object> model) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        ArrayList<Review> result = new ArrayList<>();
        review.ifPresent(result::add);

        model.put("review", result);
        return "review-info";
    }

    @GetMapping("/reviews/{id}/update")
    public String reviewUpdate(@PathVariable(value = "id") long reviewId, Map<String, Object> model) {
        Optional<Review> review = reviewRepository.findById(reviewId);

        ArrayList<Review> result = new ArrayList<>();
        review.ifPresent(result::add);

        model.put("review", result);
        return "review-update";
    }

    @PostMapping("/reviews/{id}/update")
    public String reviewsUpdateForm(@PathVariable(value = "id") long reviewId, @RequestParam String title, @RequestParam String text, Map<String, Object> model) throws ClassNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ClassNotFoundException());
        review.setTitle(title);
        review.setText(text);
        reviewRepository.save(review);

        return "redirect:/reviews/" + reviewId;
    }

    @PostMapping("/reviews/{id}/delete")
    public String reviewsDelete(@PathVariable(value = "id") long reviewId, Map<String, Object> model) throws ClassNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ClassNotFoundException());

        reviewRepository.delete(review);
        return "redirect:/reviews";
    }

    @GetMapping("/reg")
    public String reg() {
        return "reg";
    }

    @PostMapping("/reg")
    public String addUser(User user, Map<String, Object> model) {
        user.setEnabled(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping(path = "/users/")
    public String getUpdateUser(Map<String, Object> model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUsername(name);
        String email = user.getEmail();
        model.put("email", email);
        return "updateUser";
    }

    @PostMapping("/users/")
    public String updateUser(User userForm,Principal principal, Map<String, Object> model, @RequestParam String email, @RequestParam String password
            , HttpServletRequest request) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String name = auth.getName();
//        User userNew = userRepository.findByUsername(name);
//        userNew.setEmail(email);
//        userNew.setPassword(password);
//        String selectedRole = request.getParameter("roles");
//        if (selectedRole.equals("Администратор")) {
//            userNew.setEnabled(true);
//            userNew.setRoles(Collections.singleton(Role.ADMIN));
//        } else if (selectedRole.equals("Редактор")) {
//            userNew.setEnabled(true);
//            userNew.setRoles(Collections.singleton(Role.REDACTOR));
//        } else if (selectedRole.equals("Пользователь")) {
//            userNew.setEnabled(true);
//            userNew.setRoles(Collections.singleton(Role.USER));
//        }
//        model.put("email", email);
//        userRepository.save(userNew);
        // Находим пользвоателя по имени авторизованого пользователя
        User user = userRepository.findByUsername(principal.getName());
        // Устанавливаем для этого пользователя новые данные
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setRoles(userForm.getRoles());
        model.put("email", email);
        // Сохраняем (обновляем) данные про пользователя
        userRepository.save(user);
        return "updateUser";
    }
}


////        for(Role role: Role.values()){
////            String r = role.getRole();
////            model.put("role", r);
////        }
//        List <Role> role = new ArrayList<> (Arrays.asList(Role.values()));
//      //  model.put("role", r);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email =  auth.getName();
//
//        for (GrantedAuthority grantedAuthority : auth.getAuthorities()) {
//            String authority = grantedAuthority.getAuthority();
//            model.put("authority", authority);
//        }
//
////        User test = (User) authentication.getPrincipal();
//      //  User one = userRepository.findEmail(email);
//        model.put("username", email);
//
//         User user1 = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////        String emailTwo = user1.getEmail();
////
////        model.put("email", emailTwo);
////        System.out.println(user1);
