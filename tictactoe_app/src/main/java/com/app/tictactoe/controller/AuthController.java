package com.app.tictactoe.controller;

import com.app.tictactoe.model.User;
import com.app.tictactoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String dashboard(Model model) {
        User user = getCurrentUser();
        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        return "login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model) {
        User user = getCurrentUser();
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateUsername(@RequestParam String username, Model model) {
        User user = getCurrentUser();
        try {
            userService.updateUsername(user.getId(), username);
            updateAuthentication(user, username);
            model.addAttribute("success", "Username updated successfully");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("user", userService.findById(user.getId()));
        return "profile";
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.findByUsername(username);
    }

    private void updateAuthentication(User user, String newUsername) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                newUsername, authentication.getCredentials(), authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}